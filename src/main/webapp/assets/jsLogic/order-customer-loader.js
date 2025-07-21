// Hàm chuyển đổi trạng thái sang tiếng Việt
function getStatusText(status) {
    switch(status) {
        case 'CREATED':
            return 'Đã tạo';
        case 'NEGOTIATING':
            return 'Đang thương lượng';
        case 'DEPOSIT':
            return 'Giai đoạn cọc';
        case 'CONFIRMED':
            return 'Đã xác nhận';
        case 'SHIPPED':
            return 'Đã gửi hàng';
        case 'DELIVERED':
            return 'Đã giao hàng';
        case 'CANCELLED':
            return 'Đã hủy';
        case 'REJECTED':
            return 'Đã từ chối';
        case 'UNKNOWN':
            return 'Không xác định';
        default:
            return status || 'Không xác định';
    }
}

// Hàm xác định class CSS cho trạng thái
function getStatusClass(status) {
    switch(status) {
        case 'CREATED':
            return 'badge status-badge'; // Xám
        case 'NEGOTIATING':
            return 'badge status-badge'; // Vàng
        case 'DEPOSIT':
            return 'badge status-badge'; // Xanh dương nhạt
        case 'CONFIRMED':
            return 'badge status-badge'; // Xanh dương
        case 'SHIPPED':
            return 'badge status-badge'; // Xanh dương nhạt
        case 'DELIVERED':
            return 'badge status-badge'; // Xanh lá
        case 'CANCELLED':
            return 'badge status-badge'; // Đỏ
        case 'REJECTED':
            return 'badge status-badge'; // Đỏ
        case 'UNKNOWN':
            return 'badge status-badge'; // Xám
        default:
            return 'badge status-badge'; // Xám
    }
}

// Hàm xác định màu nền cho trạng thái
function getStatusBackgroundColor(status) {
    switch(status) {
        case 'CREATED':
            return '#6c757d'; // Xám
        case 'NEGOTIATING':
            return '#ffc107'; // Vàng
        case 'DEPOSIT':
            return '#0dcaf0'; // Xanh dương nhạt
        case 'CONFIRMED':
            return '#0d6efd'; // Xanh dương
        case 'SHIPPED':
            return '#0dcaf0'; // Xanh dương nhạt
        case 'DELIVERED':
            return '#198754'; // Xanh lá
        case 'CANCELLED':
            return '#dc3545'; // Đỏ
        case 'REJECTED':
            return '#dc3545'; // Đỏ
        case 'UNKNOWN':
            return '#6c757d'; // Xám
        default:
            return '#6c757d'; // Xám
    }
}

// Biến lưu trữ tất cả orders và filter hiện tại
let allOrders = [];
let currentStatusFilter = 'ALL';

// Fetch và render danh sách order cho customer
function fetchAndRenderOrders() {
    fetch('/api/orders')
        .then(res => {
            if (!res.ok) throw new Error('Không thể lấy danh sách đơn hàng');
            return res.json();
        })
        .then(orders => {
            allOrders = orders;
            renderOrderList(orders);
            renderStatusFilter();
        })
        .catch(err => {
            document.getElementById('order-list-container').innerHTML = `<div class="alert alert-danger">${err.message}</div>`;
        });
}

function renderOrderList(orders) {
    if (!orders || orders.length === 0) {
        document.getElementById('order-list-container').innerHTML = '<div class="alert alert-info">Bạn chưa có đơn hàng nào.</div>';
        return;
    }
    
    // Filter orders theo status nếu có
    let filteredOrders = orders;
    if (currentStatusFilter !== 'ALL') {
        filteredOrders = orders.filter(order => order.status === currentStatusFilter);
    }
    
    if (filteredOrders.length === 0) {
        document.getElementById('order-list-container').innerHTML = '<div class="alert alert-info">Không có đơn hàng nào với trạng thái này.</div>';
        return;
    }
    
    let html = '<div class="order-contain">';

    filteredOrders.forEach(order => {
        const isNegotiating = order.status === 'NEGOTIATING';
        const isDeposit = order.status === 'DEPOSIT';
        html += `
        <div class="order-box dashboard-bg-box mb-0 w-100">
            <div class="order-container">
                <div class="order-icon">
                    <i data-feather="box"></i>
                </div>
                <div class="order-detail">
                    <h4>Mã đơn: #${order.orderId} <span class="${getStatusClass(order.status)}" style="background: ${getStatusBackgroundColor(order.status)} !important; background-image: none !important;">${getStatusText(order.status)}</span></h4>
                    <h6 class="text-content">Tổng tiền hàng: ${order.totalAmount ? order.totalAmount.toLocaleString('vi-VN', {minimumFractionDigits: 0, maximumFractionDigits: 0}) + ' VNĐ' : ''} | Phí ship: ${order.shippingFee ? order.shippingFee.toLocaleString('vi-VN', {minimumFractionDigits: 0, maximumFractionDigits: 0}) + ' VNĐ' : ''} | <strong>Tổng cộng: ${((order.totalAmount || 0) + (order.shippingFee || 0)).toLocaleString('vi-VN', {minimumFractionDigits: 0, maximumFractionDigits: 0}) + ' VNĐ'}</strong></h6>
                </div>
            </div>
            <div class="d-flex justify-content-end align-items-center mt-2">
                <button class="btn btn-sm btn-primary" onclick="showOrderDetail(${order.orderId})">Xem chi tiết</button>
                ${isNegotiating ? `<button class="btn btn-sm btn-warning ms-2" onclick="goToDealPage(${order.orderId})">Deal</button>` : ''}
                ${isDeposit ? `<button class="btn btn-sm btn-info ms-2" onclick="goToDealPage(${order.orderId})">Chat với Seller</button>` : ''}
            </div>
        </div>`;
    });
    document.getElementById('order-list-container').innerHTML = html;
    if (window.feather) feather.replace();
}

// Hiển thị chi tiết order item bằng modal
function showOrderDetail(orderId) {
    fetch(`/api/orders/${orderId}`)
        .then(res => {
            if (!res.ok) throw new Error('Không thể lấy chi tiết đơn hàng');
            return res.json();
        })
        .then(order => {
            renderOrderItems(order);
            const modal = new bootstrap.Modal(document.getElementById('orderDetailModal'));
            modal.show();
        })
        .catch(err => {
            document.getElementById('order-items-container').innerHTML = `<div class='alert alert-danger'>${err.message}</div>`;
            const modal = new bootstrap.Modal(document.getElementById('orderDetailModal'));
            modal.show();
        });
}

function renderOrderItems(order) {
    if (!order || !order.items || order.items.length === 0) {
        document.getElementById('order-items-container').innerHTML = '<div class="alert alert-info">Đơn hàng không có sản phẩm.</div>';
        return;
    }
    let html = `<h5>Mã đơn: #${order.orderId}</h5>`;
    html += `<div class='mb-2'>Tổng tiền hàng: <b>${order.totalAmount ? order.totalAmount.toLocaleString('vi-VN', {minimumFractionDigits: 0, maximumFractionDigits: 0}) + ' VNĐ' : ''}</b> | Phí ship: <b>${order.shippingFee ? order.shippingFee.toLocaleString('vi-VN', {minimumFractionDigits: 0, maximumFractionDigits: 0}) + ' VNĐ' : ''}</b> | <strong>Tổng cộng: ${((order.totalAmount || 0) + (order.shippingFee || 0)).toLocaleString('vi-VN', {minimumFractionDigits: 0, maximumFractionDigits: 0}) + ' VNĐ'}</strong> | Trạng thái: <span class="${getStatusClass(order.status)}" style="background: ${getStatusBackgroundColor(order.status)} !important; background-image: none !important;">${getStatusText(order.status)}</span></div>`;
    html += `<table class="table table-bordered"><thead><tr><th>Sản phẩm</th><th>Số lượng</th><th>Đơn giá</th><th>Thành tiền</th></tr></thead><tbody>`;
    order.items.forEach(item => {
        html += `<tr>
            <td>${item.productName || ''}</td>
            <td>${item.quantity || ''}</td>
            <td>${item.unitPrice ? item.unitPrice.toLocaleString('vi-VN', {minimumFractionDigits: 0, maximumFractionDigits: 0}) + ' VNĐ' : ''}</td>
            <td>${item.subTotal ? item.subTotal.toLocaleString('vi-VN', {minimumFractionDigits: 0, maximumFractionDigits: 0}) + ' VNĐ' : ''}</td>
        </tr>`;
    });
    html += '</tbody></table>';
    document.getElementById('order-items-container').innerHTML = html;
}

// Tự động fetch khi tab Order được mở
function setupOrderTabAutoLoad() {
    const orderTab = document.querySelector('button[data-bs-target="#pills-order"]');
    if (orderTab) {
        orderTab.addEventListener('shown.bs.tab', fetchAndRenderOrders);
    }
    // Nếu tab đang active khi load trang thì fetch luôn
    if (document.getElementById('pills-order').classList.contains('active')) {
        fetchAndRenderOrders();
    }
}

// Hàm chuyển đến trang deal
function goToDealPage(orderId) {
    // Tạm thời chuyển đến trang deal với orderId
    window.location.href = `deal.html?orderId=${orderId}`;
}

// Hàm render filter theo status
function renderStatusFilter() {
    const filterContainer = document.getElementById('order-filter-container');
    if (!filterContainer) return;
    
    // Lấy tất cả status có trong orders
    const statuses = [...new Set(allOrders.map(order => order.status))];
    
    let html = `
        <div class="mb-3">
            <label class="form-label">Lọc theo trạng thái:</label>
            <div class="d-flex flex-wrap gap-2">
                <button class="btn btn-sm ${currentStatusFilter === 'ALL' ? 'btn-primary' : 'btn-outline-primary'}" 
                        onclick="filterByStatus('ALL')">
                    Tất cả (${allOrders.length})
                </button>`;
    
    statuses.forEach(status => {
        const count = allOrders.filter(order => order.status === status).length;
        const isActive = currentStatusFilter === status;
        html += `
            <button class="btn btn-sm ${isActive ? 'btn-primary' : 'btn-outline-primary'}" 
                    onclick="filterByStatus('${status}')">
                ${getStatusText(status)} (${count})
            </button>`;
    });
    
    html += `
            </div>
        </div>`;
    
    filterContainer.innerHTML = html;
}

// Hàm filter theo status
function filterByStatus(status) {
    currentStatusFilter = status;
    renderOrderList(allOrders);
    renderStatusFilter();
}

document.addEventListener('DOMContentLoaded', setupOrderTabAutoLoad);
// Cho phép gọi showOrderDetail từ HTML
window.showOrderDetail = showOrderDetail;
// Cho phép gọi goToDealPage từ HTML
window.goToDealPage = goToDealPage;
// Cho phép gọi filterByStatus từ HTML
window.filterByStatus = filterByStatus; 