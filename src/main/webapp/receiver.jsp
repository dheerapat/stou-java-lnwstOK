<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:layout title="Receiver" activePage="receiver">
    <h2 class="text-2xl font-bold text-gray-800">รับสินค้าเข้า (Receiver)</h2>

    <c:if test="${not empty error}">
        <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4">
            ${error}
        </div>
    </c:if>

    <div class="bg-white p-6 rounded-2xl shadow-sm border border-gray-100 max-w-md mt-6">
        <form action="receiver" method="POST" class="space-y-4">
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
                <label class="block text-sm font-bold mb-1">Lot Number</label>
                <input type="text" name="lotNumber" required class="w-full bg-gray-50 border border-gray-200 rounded-lg p-2 focus:ring-2 focus:ring-red-500 outline-none">
            </div>
            <div>
                <label class="block text-sm font-bold mb-1">จำนวน</label>
                <input type="number" name="quantity" min="1" required class="w-full bg-gray-50 border border-gray-200 rounded-lg p-2 focus:ring-2 focus:ring-red-500 outline-none">
            </div>
            <button type="submit" class="w-full bg-blue-600 text-white py-2 rounded-lg hover:bg-blue-700 transition">บันทึกรับเข้า</button>
        </form>
    </div>
</t:layout>