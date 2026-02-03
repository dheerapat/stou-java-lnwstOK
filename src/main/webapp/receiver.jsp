<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="Receiver" activePage="receiver">
    <h2 class="text-2xl font-bold text-gray-800">รับสินค้าเข้า (Receiver)</h2>
    <div class="bg-white p-6 rounded-2xl shadow-sm border border-gray-100 max-w-md mt-6">
        <form action="receiveServlet" method="POST" class="space-y-4">
            <div>
                <label class="block text-sm font-bold mb-1">P_ID</label>
                <input type="text" name="sku" required class="w-full bg-gray-50 border border-gray-200 rounded-lg p-2 focus:ring-2 focus:ring-red-500 outline-none">
            </div>
            <div>
                <label class="block text-sm font-bold mb-1">จำนวน</label>
                <input type="number" name="qty" required class="w-full bg-gray-50 border border-gray-200 rounded-lg p-2 focus:ring-2 focus:ring-red-500 outline-none">
            </div>
            <div>
                <label class="block text-sm font-bold mb-1">Location</label>
                <select name="location" class="w-full bg-gray-50 border border-gray-200 rounded-lg p-2 outline-none focus:ring-2 focus:ring-blue-500">
                    <option value="A">Warehouse 101</option>
                    <option value="B">Warehouse 102</option>
                </select>
            </div>
            <button type="submit" class="w-full bg-blue-600 text-white py-2 rounded-lg hover:bg-blue-700 transition">บันทึกรับเข้า</button>
        </form>
    </div>
</t:layout>