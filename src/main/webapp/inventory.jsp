<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="Inventory" activePage="inventory">
    <h2 class="text-2xl font-bold text-gray-800">คลังสินค้า (Inventory)</h2>

    <div class="bg-white rounded-2xl shadow-sm border border-gray-100 overflow-hidden mt-6">
        <table class="w-full text-left text-sm">
            <thead class="bg-gray-50 border-b">
                <tr>
                    <th class="px-6 py-4 font-bold text-gray-700">P_ID</th>
                    <th class="px-6 py-4 font-bold text-gray-700">ชื่อสินค้า</th>
                    <th class="px-6 py-4 font-bold text-gray-700">จำนวน</th>
                    <th class="px-6 py-4 font-bold text-gray-700">Location</th>
                    <th class="px-6 py-4 font-bold text-gray-700">สถานะ</th>
                </tr>
            </thead>
            <tbody id="inventory-data" class="divide-y">
                <tr><td colspan="5" class="px-6 py-4 text-center text-gray-400">กำลังโหลดข้อมูล...</td></tr>
            </tbody>
        </table>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', () => {
             const mockData = [
                { sku: 'P_ID-001', name: 'กล่องไซส์ A', qty: 500, location: 'Warehouse 101' , status: 'ปกติ'},
                { sku: 'P_ID-999', name: 'เทปกาว', qty: 10, location: 'Warehouse 102' , status: 'สต็อกต่ำ'},
            ];
            const container = document.getElementById('inventory-data');
            container.innerHTML = mockData.map(item => `
                <tr class="border-b">
                    <td class="px-6 py-4">\${item.sku}</td>
                    <td class="px-6 py-4 font-medium">\${item.name}</td>
                    <td class="px-6 py-4">\${item.qty} ชิ้น</td>
                    <td class="px-6 py-4">\${item.location}</td>
                    <td class="px-6 py-4 \${item.status === 'สต็อกต่ำ' ? 'text-red-600 font-bold' : 'text-emerald-500'}">
                        \${item.status}
                    </td>
                </tr>
            `).join('');
        });
    </script>
</t:layout>