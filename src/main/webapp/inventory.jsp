<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:layout title="Inventory" activePage="inventory">
    <h2 class="text-2xl font-bold text-gray-800">คลังสินค้า (Inventory)</h2>

    <c:if test="${not empty error}">
        <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4">
            ${error}
        </div>
    </c:if>

    <c:if test="${not empty success}">
        <div class="bg-emerald-100 border border-emerald-400 text-emerald-700 px-4 py-3 rounded mb-4">
            ${success}
        </div>
    </c:if>

    <div class="bg-white rounded-2xl shadow-sm border border-gray-100 p-4 mb-6">
        <form action="inventory" method="GET" class="flex gap-4 items-end">
            <div class="flex-1">
                <label class="block text-sm font-bold mb-1 text-gray-700">Filter by Product</label>
                <select name="productId" class="w-full bg-gray-50 border border-gray-200 rounded-lg p-2 outline-none focus:ring-2 focus:ring-blue-500">
                    <option value="">All Products</option>
                    <c:forEach items="${products}" var="product">
                        <option value="${product.productId}" ${selectedProductId == product.productId ? 'selected' : ''}>${product.productName}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="flex-1">
                <label class="block text-sm font-bold mb-1 text-gray-700">Filter by Location</label>
                <select name="locationId" class="w-full bg-gray-50 border border-gray-200 rounded-lg p-2 outline-none focus:ring-2 focus:ring-blue-500">
                    <option value="">All Locations</option>
                    <c:forEach items="${locations}" var="location">
                        <option value="${location.locationId}" ${selectedLocationId == location.locationId ? 'selected' : ''}>${location.locationName}</option>
                    </c:forEach>
                </select>
            </div>
            <div>
                <button type="submit" class="bg-blue-600 text-white px-6 py-2 rounded-lg hover:bg-blue-700 transition">Filter</button>
            </div>
        </form>
    </div>

    <div class="bg-white rounded-2xl shadow-sm border border-gray-100 overflow-hidden">
        <table class="w-full text-left text-sm">
            <thead class="bg-gray-50 border-b">
                <tr>
                    <th class="px-6 py-4 font-bold text-gray-700">P_ID</th>
                    <th class="px-6 py-4 font-bold text-gray-700">ชื่อสินค้า</th>
                    <th class="px-6 py-4 font-bold text-gray-700">จำนวน</th>
                    <th class="px-6 py-4 font-bold text-gray-700">Location</th>
                    <th class="px-6 py-4 font-bold text-gray-700">Lot Number</th>
                    <th class="px-6 py-4 font-bold text-gray-700">สถานะ</th>
                </tr>
            </thead>
            <tbody class="divide-y">
                <c:choose>
                    <c:when test="${empty stockList}">
                        <tr>
                            <td colspan="6" class="px-6 py-8 text-center text-gray-400">No inventory data found</td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach items="${stockList}" var="stock">
                            <tr class="border-b">
                                <td class="px-6 py-4">P_ID-${stock.product.productId}</td>
                                <td class="px-6 py-4 font-medium">${stock.product.productName}</td>
                                <td class="px-6 py-4">${stock.quantity} ${stock.product.unit}</td>
                                <td class="px-6 py-4">${stock.location.locationName}</td>
                                <td class="px-6 py-4">${stock.lotNumber}</td>
                                <td class="px-6 py-4 ${stock.quantity < 20 ? 'text-red-600 font-bold' : 'text-emerald-500'}">
                                    ${stock.quantity < 20 ? 'สต็อกต่ำ' : 'ปกติ'}
                                </td>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </div>
</t:layout>