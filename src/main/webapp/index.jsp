<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="Dashboard" activePage="dashboard">
    <h2 class="text-2xl font-bold text-gray-800">DashBoard</h2>
    <p class="text-gray-500 text-sm mb-8">Overview!</p>

    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
        <div class="bg-white p-6 rounded-2xl shadow-sm border border-gray-100">
            <div class="flex justify-between items-start mb-4">
                <div><p class="text-gray-500 text-xs font-semibold mb-1">Inventory Total</p><h3 class="text-2xl font-bold">1,234</h3></div>
                <div class="p-2 bg-emerald-50 text-emerald-500 rounded-lg"><i class="fas fa-cubes"></i></div>
            </div>
            <p class="text-emerald-500 text-[10px] font-bold">+12% <span class="text-gray-400 font-normal ml-1">จากเดือนก่อน</span></p>
        </div>
    </div>

    <div class="bg-white p-6 rounded-2xl shadow-sm border border-gray-100">
        <h4 class="font-bold text-gray-800 mb-6">Recent log</h4>
        <div class="space-y-6">
            <div class="flex items-center justify-between">
                <div class="flex items-center gap-4">
                    <div class="w-10 h-10 bg-emerald-100 text-emerald-600 rounded-full flex items-center justify-center"><i class="fas fa-arrow-down text-sm"></i></div>
                    <div><p class="text-sm font-bold text-gray-800">รับสินค้าเข้าคลัง</p><p class="text-xs text-gray-500">P_ID-001 จำนวน 100 ชิ้น</p></div>
                </div>
                <span class="text-[10px] text-gray-400">5 นาทีที่แล้ว</span>
            </div>
        </div>
    </div>
</t:layout>