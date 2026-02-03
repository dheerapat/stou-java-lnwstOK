<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:layout title="Dashboard" activePage="dashboard">
    <h2 class="text-2xl font-bold text-gray-800">Dashboard</h2>
    <p class="text-gray-500 text-sm mb-8">Overview</p>

    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
        <div class="bg-white p-6 rounded-2xl shadow-sm border border-gray-100">
            <div class="flex justify-between items-start mb-4">
                <div><p class="text-gray-500 text-xs font-semibold mb-1">Inventory Total</p><h3 class="text-2xl font-bold">${inventoryTotal}</h3></div>
                <div class="p-2 bg-emerald-50 text-emerald-500 rounded-lg"><i class="fas fa-cubes"></i></div>
            </div>
            <p class="text-emerald-500 text-[10px] font-bold"><span class="text-gray-400 font-normal">Total stock quantity</span></p>
        </div>
    </div>

    <div class="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <div class="bg-white p-6 rounded-2xl shadow-sm border border-gray-100">
            <h4 class="font-bold text-gray-800 mb-6">Recent Stock In</h4>
            <div class="space-y-4">
                <c:choose>
                    <c:when test="${empty recentAdds}">
                        <p class="text-gray-400 text-sm">No recent stock additions</p>
                    </c:when>
                    <c:otherwise>
                        <c:forEach items="${recentAdds}" var="add">
                            <div class="flex items-center justify-between">
                                <div class="flex items-center gap-4">
                                    <div class="w-10 h-10 bg-emerald-100 text-emerald-600 rounded-full flex items-center justify-center"><i class="fas fa-arrow-down text-sm"></i></div>
                                    <div>
                                        <p class="text-sm font-bold text-gray-800">รับสินค้าเข้าคลัง</p>
                                        <p class="text-xs text-gray-500">P_ID-${add.stock.product.productId} ${add.stock.product.productName} จำนวน ${add.quantity} ${add.stock.product.unit}</p>
                                    </div>
                                </div>
                                <span class="text-[10px] text-gray-400"><fmt:formatDate value="${add.addDate}" pattern="dd/MM/yyyy HH:mm"/></span>
                            </div>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

        <div class="bg-white p-6 rounded-2xl shadow-sm border border-gray-100">
            <h4 class="font-bold text-gray-800 mb-6">Recent Stock Out</h4>
            <div class="space-y-4">
                <c:choose>
                    <c:when test="${empty recentWithdraws}">
                        <p class="text-gray-400 text-sm">No recent stock withdrawals</p>
                    </c:when>
                    <c:otherwise>
                        <c:forEach items="${recentWithdraws}" var="withdraw">
                            <div class="flex items-center justify-between">
                                <div class="flex items-center gap-4">
                                    <div class="w-10 h-10 bg-red-100 text-red-600 rounded-full flex items-center justify-center"><i class="fas fa-arrow-up text-sm"></i></div>
                                    <div>
                                        <p class="text-sm font-bold text-gray-800">จ่ายสินค้าออกคลัง</p>
                                        <p class="text-xs text-gray-500">P_ID-${withdraw.stock.product.productId} ${withdraw.stock.product.productName} จำนวน ${withdraw.quantity} ${withdraw.stock.product.unit}</p>
                                    </div>
                                </div>
                                <span class="text-[10px] text-gray-400"><fmt:formatDate value="${withdraw.withdrawDate}" pattern="dd/MM/yyyy HH:mm"/></span>
                            </div>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</t:layout>