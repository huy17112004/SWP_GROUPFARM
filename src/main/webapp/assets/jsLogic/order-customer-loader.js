// Fetch và render danh sách order cho customer
function fetchAndRenderOrders() {
    fetch('/api/orders')
        .then(res => {
            if (!res.ok) throw new Error('Không thể lấy danh sách đơn hàng');
            return res.json();
        })
        .then(orders => {
            renderOrderList(orders);
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
    let html = '<div class="order-contain">';

    orders.forEach(order => {
        const isNegotiating = order.status === 'NEGOTIATING';
        const isDeposit = order.status === 'DEPOSIT';
        html += `
        <div class="order-box dashboard-bg-box mb-0 w-100">
            <div class="order-container">
                <div class="order-icon">
                    <i data-feather="box"></i>
                </div>
                <div class="order-detail">
                    <h4>Mã đơn: #${order.orderId} <span class="${order.status === 'Success' ? 'success-bg' : ''}">${order.status || ''}</span></h4>
                    <h6 class="text-content">Tổng tiền: ${order.totalAmount ? order.totalAmount.toLocaleString() : ''} | Phí ship: ${order.shippingFee ? order.shippingFee.toLocaleString() : ''}</h6>
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
    html += `<div class='mb-2'>Tổng tiền: <b>${order.totalAmount ? order.totalAmount.toLocaleString() : ''}</b> | Phí ship: <b>${order.shippingFee ? order.shippingFee.toLocaleString() : ''}</b> | Trạng thái: <b>${order.status || ''}</b></div>`;
    html += `<table class="table table-bordered"><thead><tr><th>Sản phẩm</th><th>Số lượng</th><th>Đơn giá</th><th>Thành tiền</th></tr></thead><tbody>`;
    order.items.forEach(item => {
        html += `<tr>
            <td>${item.productName || ''}</td>
            <td>${item.quantity || ''}</td>
            <td>${item.unitPrice ? item.unitPrice.toLocaleString() : ''}</td>
            <td>${item.subTotal ? item.subTotal.toLocaleString() : ''}</td>
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

document.addEventListener('DOMContentLoaded', setupOrderTabAutoLoad);
// Cho phép gọi showOrderDetail từ HTML
window.showOrderDetail = showOrderDetail;
// Cho phép gọi goToDealPage từ HTML
window.goToDealPage = goToDealPage; 