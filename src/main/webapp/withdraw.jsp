<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:layout title="Withdraw" activePage="withdraw">
    <h2 class="text-2xl font-bold text-gray-800">รับสินค้าออก (Withdraw)</h2>

    <c:if test="${not empty error}">
        <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4">
            ${error}
        </div>
    </c:if>

    <div class="bg-white p-6 rounded-2xl shadow-sm border border-gray-100 max-w-md mt-6">
        <form action="withdraw" method="POST" class="space-y-4">

            <c:if test="${empty selectedStock}">
                <div>
                    <label class="block text-sm font-bold mb-1">Product</label>
                    <select name="productId" required class="w-full bg-gray-50 border border-gray-200 rounded-lg p-2 outline-none focus:ring-2 focus:ring-blue-500">
                        <option value="">Select Product</option>
                        <c:forEach items="${products}" var="product">
                            <option value="${product.productId}">${product.productName} (${product.unit})</option>
                        </c:forEach>
                    </select>
                </div>

                <div>
                    <label class="block text-sm font-bold mb-1">Location</label>
                    <select name="locationId" required class="w-full bg-gray-50 border border-gray-200 rounded-lg p-2 outline-none focus:ring-2 focus:ring-blue-500">
                        <option value="">Select Location</option>
                        <c:forEach items="${locations}" var="location">
                            <option value="${location.locationId}">${location.locationName}</option>
                        </c:forEach>
                    </select>
                </div>

                <div>
                    <label class="block text-sm font-bold mb-1">จำนวน</label>
                    <input type="number" name="quantity" min="1" required class="w-full bg-gray-50 border border-gray-200 rounded-lg p-2 focus:ring-2 focus:ring-red-500 outline-none">
                </div>

                <button type="submit" class="w-full bg-blue-600 text-white py-2 rounded-lg hover:bg-blue-700 transition">Check Availability</button>
            </c:if>

            <c:if test="${not empty selectedStock}">
                <div class="bg-blue-50 border border-blue-200 p-4 rounded-lg mb-4">
                    <p class="font-bold text-blue-800 mb-2">Lot Information</p>
                    <p class="text-sm text-blue-700">
                        Product: <strong>${selectedStock.product.productName}</strong><br>
                        Location: <strong>${selectedStock.location.locationName}</strong><br>
                        Will withdraw from Lot: <strong>${selectedStock.lotNumber}</strong><br>
                        Available quantity: <strong>${selectedStock.quantity} ${selectedStock.product.unit}</strong>
                    </p>
                </div>

                <input type="hidden" name="productId" value="${selectedStock.product.productId}">
                <input type="hidden" name="locationId" value="${selectedStock.location.locationId}">
                <input type="hidden" name="quantity" value="${quantity}">
                <input type="hidden" name="confirmed" value="true">

                <button type="submit" class="w-full bg-red-600 text-white py-2 rounded-lg hover:bg-red-700 transition">
                    Confirm Withdrawal of ${quantity} ${selectedStock.product.unit}
                </button>
            </c:if>

        </form>
    </div>
</t:layout>
