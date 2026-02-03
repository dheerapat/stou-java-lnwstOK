<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:layout title="Product Masterlist" activePage="masterlist">
    <h2 class="text-2xl font-bold text-gray-800">Product Masterlist</h2>

    <c:if test="${not empty error}">
        <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4">
            ${error}
        </div>
    </c:if>

    <div class="bg-white p-6 rounded-2xl shadow-sm border border-gray-100 max-w-md mb-6">
        <h3 class="font-bold text-gray-800 mb-4">Add/Edit Product</h3>
        <form action="products" method="POST" class="space-y-4">
            <c:if test="${not empty product}">
                <input type="hidden" name="productId" value="${product.productId}">
            </c:if>
            <div>
                <label class="block text-sm font-bold mb-1">Product Name</label>
                <input type="text" name="productName" value="${product.productName}" required class="w-full bg-gray-50 border border-gray-200 rounded-lg p-2 focus:ring-2 focus:ring-blue-500 outline-none">
            </div>
            <div>
                <label class="block text-sm font-bold mb-1">Unit</label>
                <input type="text" name="unit" value="${product.unit}" required class="w-full bg-gray-50 border border-gray-200 rounded-lg p-2 focus:ring-2 focus:ring-blue-500 outline-none">
            </div>
            <button type="submit" class="w-full bg-blue-600 text-white py-2 rounded-lg hover:bg-blue-700 transition">
                ${not empty product ? 'Update Product' : 'Add Product'}
            </button>
            <c:if test="${not empty product}">
                <a href="products" class="block w-full bg-gray-500 text-white py-2 rounded-lg hover:bg-gray-600 transition text-center">Cancel</a>
            </c:if>
        </form>
    </div>

    <div class="bg-white rounded-2xl shadow-sm border border-gray-100 overflow-hidden">
        <table class="w-full text-left text-sm">
            <thead class="bg-gray-50 border-b">
                <tr>
                    <th class="px-6 py-4 font-bold text-gray-700">Product ID</th>
                    <th class="px-6 py-4 font-bold text-gray-700">Product Name</th>
                    <th class="px-6 py-4 font-bold text-gray-700">Unit</th>
                    <th class="px-6 py-4 font-bold text-gray-700">Actions</th>
                </tr>
            </thead>
            <tbody class="divide-y">
                <c:choose>
                    <c:when test="${empty products}">
                        <tr>
                            <td colspan="4" class="px-6 py-8 text-center text-gray-400">No products found</td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach items="${products}" var="prod">
                            <tr class="border-b">
                                <td class="px-6 py-4">P_ID-${prod.productId}</td>
                                <td class="px-6 py-4 font-medium">${prod.productName}</td>
                                <td class="px-6 py-4">${prod.unit}</td>
                                <td class="px-6 py-4 space-x-2">
                                    <a href="products?action=edit&id=${prod.productId}" class="text-blue-600 hover:underline">Edit</a>
                                    <a href="products?action=delete&id=${prod.productId}" class="text-red-600 hover:underline" onclick="return confirm('Are you sure you want to delete this product?');">Delete</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </div>
</t:layout>
