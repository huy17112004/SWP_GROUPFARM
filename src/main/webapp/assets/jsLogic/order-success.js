// order-success.js

// Lấy orderId từ query string
function getOrderIdFromUrl() {
    const params = new URLSearchParams(window.location.search);
    return params.get('orderId');
}

function formatCurrencyVND(value) {
    if (value == null) return '';
    return Number(value).toLocaleString('vi-VN') + ' ₫';
}

function renderOrderInfo(order) {
    // Mã giao dịch
    document.getElementById('order-code').textContent = order.orderCode || order.orderId || '';
    // Tổng tiền hàng
    document.getElementById('total-item').textContent = formatCurrencyVND(order.totalItem);
    // Tiền ship
    document.getElementById('ship-fee').textContent = formatCurrencyVND(order.estimatedShipFee);
    // Tổng cộng
    document.getElementById('total-price').textContent = formatCurrencyVND(order.totalPrice);
    // Số lượng sản phẩm
    if (order.items) {
        document.getElementById('total-item-label').textContent = `(${order.items.length} sản phẩm)`;
    }
    // Địa chỉ giao hàng
    document.getElementById('delivery-address').textContent = order.deliveryAddress || '';
    // Ngày giao dự kiến
    if (order.deliveryDate) {
        const date = new Date(order.deliveryDate);
        const dateStr = date.toLocaleDateString('vi-VN');
        document.getElementById('delivery-date').textContent = dateStr;
        document.getElementById('delivery-date-label').textContent = dateStr;
    }
    // Render sản phẩm
    renderOrderItems(order.items || []);
}

function renderOrderItems(items) {
    const tbody = document.getElementById('order-items-body');
    tbody.innerHTML = '';
    if (!items.length) {
        tbody.innerHTML = '<tr><td colspan="4">Không có sản phẩm nào trong đơn hàng.</td></tr>';
        return;
    }
    items.forEach(item => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td class="product-detail">
                <div class="product border-0">
                    <a href="#" class="product-image">
                        <img src="${item.productImage || '../assets/images/vegetable/product/1.png'}" class="img-fluid blur-up lazyload" alt="">
                    </a>
                    <div class="product-detail">
                        <ul>
                            <li class="name">
                                <a href="#">${item.productName || ''}</a>
                            </li>
                            <li class="text-content">Bán bởi: ${item.sellerName || ''}</li>
                            <li class="text-content">Số lượng - ${item.quantity || 1} ${item.unit || ''}</li>
                        </ul>
                    </div>
                </div>
            </td>
            <td class="price">
                <h4 class="table-title text-content">Giá</h4>
                <h6 class="theme-color">${formatCurrencyVND(item.price)}</h6>
            </td>
            <td class="quantity">
                <h4 class="table-title text-content">SL</h4>
                <h4 class="text-title">${item.quantity || 1}</h4>
            </td>
            <td class="subtotal">
                <h4 class="table-title text-content">Tổng</h4>
                <h5>${formatCurrencyVND(item.totalPrice)}</h5>
            </td>
        `;
        tbody.appendChild(tr);
    });
}

// Main
window.addEventListener('DOMContentLoaded', function() {
    const orderId = getOrderIdFromUrl();
    if (!orderId) return;
    fetch(`/api/order-detail?orderId=${orderId}`)
        .then(res => res.json())
        .then(order => {
            renderOrderInfo(order);
        })
        .catch(err => {
            console.error('Lỗi lấy thông tin đơn hàng:', err);
        });
}); 