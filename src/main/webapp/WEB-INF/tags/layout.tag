<%@tag description="Warehouse Layout" pageEncoding="UTF-8"%>
<%@attribute name="title" required="true" %>
<%@attribute name="activePage" required="true" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="th">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${title} - lnwstOK</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Sarabun:wght@300;400;600&display=swap" rel="stylesheet">
    <style>
       body { font-family: 'Sarabun', sans-serif; background-color: #f8fafc; }
       /* Standard active class */
       .menu-active {
            background-color: #1e293b !important;
            color: white !important;
            border-left: 4px solid #3b82f6;
        }
       /* Inactive link style */
       .menu-inactive {
            color: #9ca3af; /* text-gray-400 */
       }
       .menu-inactive:hover {
            background-color: #1e293b;
       }
    </style>
</head>
<body class="flex h-screen overflow-hidden">

    <aside class="w-64 bg-[#111827] text-gray-300 flex flex-col">
        <div class="p-6 flex items-center gap-3">
            <div class="w-8 h-8 bg-[#10b981] rounded-lg flex items-center justify-center text-white">
                <i class="fas fa-warehouse"></i>
            </div>
            <div>
                <h1 class="text-white font-bold text-sm leading-tight">lnwstOK</h1>
                <p class="text-[10px] text-gray-500">Warehouse Management</p>
            </div>
        </div>

        <nav class="flex-1 px-4 space-y-2">
            <p class="text-[10px] uppercase text-gray-500 font-bold px-2 mb-2">Main Menu</p>

            <a href="dashboard" class="flex items-center gap-3 px-4 py-2.5 rounded-lg transition ${activePage == 'dashboard' ? 'menu-active' : 'menu-inactive'}">
                <i class="fas fa-th-large text-sm"></i> <span class="text-sm">Dashboard</span>
            </a>

            <a href="products" class="flex items-center gap-3 px-4 py-2.5 rounded-lg transition ${activePage == 'masterlist' ? 'menu-active' : 'menu-inactive'}">
                <i class="fas fa-box text-sm"></i> <span class="text-sm">Product Masterlist</span>
            </a>

            <a href="inventory" class="flex items-center gap-3 px-4 py-2.5 rounded-lg transition ${activePage == 'inventory' ? 'menu-active' : 'menu-inactive'}">
                <i class="fas fa-boxes text-sm"></i> <span class="text-sm">Inventory</span>
            </a>

            <a href="receiver" class="flex items-center gap-3 px-4 py-2.5 rounded-lg transition ${activePage == 'receiver' ? 'menu-active' : 'menu-inactive'}">
                <i class="fas fa-download text-sm"></i> <span class="text-sm">Receiver</span>
            </a>

            <a href="withdraw" class="flex items-center gap-3 px-4 py-2.5 rounded-lg transition ${activePage == 'withdraw' ? 'menu-active' : 'menu-inactive'}">
                <i class="fas fa-upload text-sm"></i> <span class="text-sm">Withdraw</span>
            </a>
        </nav>

        <div class="p-4 border-t border-gray-800">
            <div class="flex items-center gap-3 p-2">
                <div class="w-8 h-8 bg-blue-500 rounded-full flex items-center justify-center text-xs text-white">AD</div>
                <div class="overflow-hidden">
                    <p class="text-xs font-bold text-white truncate">Admin Contact</p>
                    <p class="text-[10px] text-gray-500 truncate">admin@lnwstOK.com</p>
                </div>
            </div>
        </div>
    </aside>

    <main class="flex-1 flex flex-col overflow-y-auto">
        <header class="h-16 bg-white border-b flex items-center justify-between px-8 sticky top-0 z-10">
            <div class="relative w-96">
                <span class="absolute inset-y-0 left-3 flex items-center text-gray-400">
                    <i class="fas fa-search"></i>
                </span>
                <input type="text" class="w-full bg-gray-100 border-none rounded-full py-2 pl-10 pr-4 text-sm focus:ring-2 focus:ring-blue-500" placeholder="ค้นหาสินค้า, รหัส SKU...">
            </div>
        </header>

        <div class="p-8">
            <jsp:doBody/>
        </div>
    </main>

</body>
</html>