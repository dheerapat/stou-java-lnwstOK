package org.example.servlet;

import org.example.model.AddStock;
import org.example.model.Stock;
import org.example.model.WithdrawStock;
import org.example.service.AddStockService;
import org.example.service.StockService;
import org.example.service.WithdrawStockService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DashboardServlet extends HttpServlet {
    private final StockService stockService = new StockService();
    private final AddStockService addStockService = new AddStockService();
    private final WithdrawStockService withdrawStockService = new WithdrawStockService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Stock> allStock = stockService.getAllStockInfo();
            int inventoryTotal = 0;
            for (Stock stock : allStock) {
                inventoryTotal += stock.getQuantity();
            }

            List<AddStock> recentAdds = addStockService.getAllAddStock();
            List<AddStock> recentAddsLimited = new ArrayList<>();
            int limit = Math.min(recentAdds.size(), 5);
            for (int i = 0; i < limit; i++) {
                recentAddsLimited.add(recentAdds.get(i));
            }

            List<WithdrawStock> recentWithdraws = withdrawStockService.getAllWithdrawStock();
            List<WithdrawStock> recentWithdrawsLimited = new ArrayList<>();
            limit = Math.min(recentWithdraws.size(), 5);
            for (int i = 0; i < limit; i++) {
                recentWithdrawsLimited.add(recentWithdraws.get(i));
            }

            request.setAttribute("inventoryTotal", inventoryTotal);
            request.setAttribute("recentAdds", recentAddsLimited);
            request.setAttribute("recentWithdraws", recentWithdrawsLimited);

            request.getRequestDispatcher("/index.jsp").forward(request, response);

        } catch (SQLException e) {
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }
}
