
function loadAllOrders() {
    fetch("/api/shipping-stats")
        .then(res => res.json())
        .then(data => renderAllOrders(data))
        .catch(err => console.error("Lỗi khi lấy đơn hàng:", err));
}

function renderAllOrders(orders) {
    const tbody = document.getElementById("all-order-body");
    tbody.innerHTML = "";

    orders.forEach(order => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>#${order.orderId}</td>
            <td>${order.productName}</td>        
           <td>${order.totalPrice ? Number(order.totalPrice).toLocaleString('vi-VN') + '₫' : '0₫'}</td>
            <td><span class="${order.status === 'SHIPPED' ? 'badge bg-success' : 'badge bg-warning'}">${order.status}</span></td>   
        `;
        tbody.appendChild(row);
    }
    )};

