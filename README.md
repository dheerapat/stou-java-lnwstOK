# lnwstOK - Warehouse Management System

A full-stack Java web application demonstrating classic 3-tier architecture with MVC pattern for inventory management and stock tracking.

## 🎓 Educational Purpose

This project is designed as a learning resource for students and developers who want to understand:

- **Java Servlet** - Server-side web programming
- **JSP & JSTL** - JavaServer Pages with tag libraries
- **JDBC** - Java Database Connectivity
- **MVC Architecture** - Model-View-Controller pattern
- **3-Tier Architecture** - Separation of concerns (DAO, Service, Servlet layers)
- **Transaction Management** - Database transactions with rollback support
- **FIFO Inventory System** - First-In-First-Out stock tracking
- **Docker** - Containerized database deployment

## 🏗️ Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                      Presentation Layer                      │
│                    (Servlets + JSP Views)                    │
│  DashboardServlet │ ProductServlet │ InventoryServlet       │
│  ReceiverServlet │ WithdrawServlet                         │
└──────────────────────────┬──────────────────────────────────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────────┐
│                       Business Logic Layer                   │
│                         (Services)                           │
│  StockService │ ProductService │ LocationService             │
│  AddStockService │ WithdrawStockService                     │
│  ───────────────────────────────────────────────────────   │
│  • Input Validation                                          │
│  • Business Rules                                            │
│  • Transaction Management                                   │
└──────────────────────────┬──────────────────────────────────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────────┐
│                      Data Access Layer                       │
│                          (DAOs)                              │
│  StockDAO │ ProductDAO │ LocationDAO                        │
│  AddStockDAO │ WithdrawStockDAO                            │
│  ───────────────────────────────────────────────────────   │
│  • SQL Queries                                               │
│  • Connection Management                                     │
│  • ResultSet Mapping                                         │
└──────────────────────────┬──────────────────────────────────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────────┐
│                      Database Layer                          │
│                    (MySQL 9.6.0)                            │
└─────────────────────────────────────────────────────────────┘
```

## 📁 Project Structure

```
src/main/java/org/example/
├── dao/                    # Data Access Objects
│   ├── ProductDAO.java     # CRUD operations for products
│   ├── LocationDAO.java    # CRUD operations for locations
│   ├── StockDAO.java       # Inventory queries and updates
│   ├── AddStockDAO.java    # Stock addition records
│   └── WithdrawStockDAO.java # Stock withdrawal records
├── model/                  # Data Models (POJOs)
│   ├── Product.java
│   ├── Location.java
│   ├── Stock.java
│   ├── AddStock.java
│   └── WithdrawStock.java
├── service/                # Business Logic Layer
│   ├── ProductService.java
│   ├── LocationService.java
│   ├── StockService.java   # Core inventory logic
│   ├── AddStockService.java
│   └── WithdrawStockService.java
├── servlet/                # HTTP Request Handlers (Controllers)
│   ├── DashboardServlet.java
│   ├── ProductServlet.java
│   ├── InventoryServlet.java
│   ├── ReceiverServlet.java
│   └── WithdrawServlet.java
└── util/
    └── MySQLConnection.java # Database connection utility

src/main/webapp/
├── WEB-INF/
│   ├── web.xml            # Servlet configuration
│   └── tags/
│       └── layout.tag     # Shared layout template
├── css/                   # Stylesheets
├── index.jsp              # Dashboard view
├── masterlist.jsp         # Product management
├── inventory.jsp          # Inventory view
├── receiver.jsp           # Stock receiving
└── withdraw.jsp           # Stock withdrawal
```

## ✨ Features

### 1. Dashboard
- Total inventory count
- Recent stock additions (last 5)
- Recent stock withdrawals (last 5)

### 2. Product Masterlist
- Create new products with name and unit
- View all products
- Edit product details
- Delete products

### 3. Inventory Management
- View all inventory items with details
- Filter by product
- Filter by location
- Display product name, unit, quantity, location, and lot number

### 4. Receiver (Stock In)
- Add stock to inventory
- Specify product, location, lot number, and quantity
- Automatic lot tracking
- Creates add_stock transaction record

### 5. Withdraw (Stock Out)
- FIFO (First-In-First-Out) algorithm
- Automatically selects oldest stock lot
- Confirmation before withdrawal
- Creates withdraw_stock transaction record
- Validates sufficient stock availability

## 🗄️ Database Schema

```sql
-- Locations
locations (location_id, location_name)

-- Products
products (product_id, product_name, unit)

-- Inventory
inventory (stock_id, lot_number, product_id, location_id, quantity)

-- Stock Transactions
add_stock (add_stock_id, stock_id, quantity, add_date)
withdraw_stock (withdraw_stock_id, stock_id, quantity, withdraw_date)

-- Foreign Keys
inventory.product_id → products.product_id
inventory.location_id → locations.location_id
add_stock.stock_id → inventory.stock_id
withdraw_stock.stock_id → inventory.stock_id
```

## 🚀 Getting Started

### Prerequisites
- Java Development Kit (JDK) 21 or higher
- Maven 3.6 or higher
- Docker and Docker Compose (for MySQL container)

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd lnwstOK
   ```

2. **Start MySQL database with Docker**
   ```bash
   docker-compose up -d
   ```

   This will:
   - Start MySQL container on port 3306
   - Create database `inventory_db`
   - Run initialization script `init.sql`
   - Create default locations and sample products

3. **Build the project with Maven**
   ```bash
   mvn clean package
   ```

4. **Run the application**
   ```bash
   mvn cargo:run
   ```

   The application will start on:
   ```
   http://localhost:8080/lnwstOK
   ```

### Database Connection Details

The application connects to MySQL using these credentials:

```java
URL: jdbc:mysql://localhost:3306/inventory_db
Username: admin
Password: password
```

These are configured in `src/main/java/org/example/util/MySQLConnection.java`

## 📊 Key Concepts Demonstrated

### 1. DAO Pattern
Each entity has a dedicated DAO class handling all database operations:

```java
// Example from ProductDAO.java
public List<Product> getAllProducts() throws SQLException {
    String sql = "SELECT product_id, product_name, unit FROM products";
    // ... execute query and map to Product objects
}
```

### 2. Service Layer with Validation
Business logic and validation separated from DAO:

```java
// Example from ProductService.java
public Product addProduct(String productName, String unit) throws SQLException {
    validateProduct(productName, unit);  // Validation
    productDAO.addProduct(productName, unit);  // Data access
    // ... return created product
}
```

### 3. Transaction Management
Database transactions with rollback on failure:

```java
// Example from StockService.java:46
try (Connection conn = MySQLConnection.getConnection()) {
    conn.setAutoCommit(false);
    try {
        // ... multiple database operations
        conn.commit();
    } catch (SQLException e) {
        conn.rollback();
        throw e;
    }
}
```

### 4. FIFO Inventory Algorithm
Selecting oldest stock for withdrawal:

```java
// StockDAO.java:238
public Stock getOldestStockByProductAndLocation(int productId, int locationId) {
    // Uses MIN(add_date) and ORDER BY first_add_date ASC
    // Returns the stock lot that was added first
}
```

### 5. MVC Pattern with Servlets
- **Model**: POJOs in `model/` package
- **View**: JSP files in `webapp/` directory
- **Controller**: Servlets in `servlet/` package

```java
// Example from ProductServlet.java
protected void doGet(HttpServletRequest request, HttpServletResponse response) {
    List<Product> products = productService.getAllProducts();
    request.setAttribute("products", products);
    request.getRequestDispatcher("/masterlist.jsp").forward(request, response);
}
```

### 6. JSP with JSTL
Dynamic HTML generation with tag libraries:

```jsp
<!-- Example from masterlist.jsp -->
<c:forEach items="${products}" var="product">
    <tr>
        <td>${product.productId}</td>
        <td>${product.productName}</td>
        <td>${product.unit}</td>
    </tr>
</c:forEach>
```

## 🔧 Technology Stack

| Component | Technology | Version |
|-----------|-----------|---------|
| Backend | Java | 21 |
| Servlet API | javax.servlet | 4.0.1 |
| Database | MySQL Connector | 9.6.0 |
| Template Engine | JSP with JSTL | 1.2 |
| CSS Framework | Tailwind CSS | CDN |
| Icons | Font Awesome | 6.0.0 |
| Build Tool | Maven | Latest |
| Application Server | Tomcat | 9.x (embedded) |
| Containerization | Docker & Docker Compose | Latest |

## 📚 Learning Outcomes

After studying this project, you will understand:

1. **How to structure a Java web application** using standard 3-tier architecture
2. **Separation of concerns** between data access, business logic, and presentation
3. **Database transactions** and when/how to use them
4. **JDBC best practices** including PreparedStatement to prevent SQL injection
5. **Servlet lifecycle** and request/response handling
6. **JSP and JSTL** for server-side rendering
7. **FIFO algorithm** implementation for inventory management
8. **Docker basics** for containerized database deployment

## 🎯 Project Stats

- **Total Java Code**: ~1,865 lines
- **Servlets**: 5 controllers
- **DAOs**: 5 data access objects
- **Services**: 5 business logic classes
- **Models**: 5 data models
- **JSP Views**: 4 pages + 1 shared layout

## ⚠️ Limitations & Educational Notes

This project is intentionally simple for educational purposes and should NOT be used in production:

1. **No Authentication**: Anyone can access and modify data
2. **Hardcoded Credentials**: Database credentials are in plain text
3. **No Connection Pooling**: Creates new connection per request
4. **Basic Error Handling**: Limited error recovery mechanisms
5. **No Unit Tests**: Test directory exists but no tests implemented
6. **Missing Servlet**: LocationServlet referenced in web.xml but not implemented
7. **Single Session**: Not designed for concurrent users

**Recommended Next Steps for Learning:**

- Add authentication and authorization (Spring Security or manual session management)
- Implement connection pooling (HikariCP, Apache DBCP)
- Add unit tests with JUnit and Mockito
- Add logging framework (SLF4J + Logback)
- Implement API versioning
- Add input sanitization and CSRF protection
- Move to modern framework (Spring Boot) after understanding basics

## 📝 License

This is an educational project. Feel free to use it for learning purposes.

## 🤝 Contributing

This is a learning project. Feel free to fork, modify, and experiment with it!

## 📧 Questions

For questions about this educational project, please refer to the code comments and the project documentation.
