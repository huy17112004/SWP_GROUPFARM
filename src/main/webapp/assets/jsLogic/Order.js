document.addEventListener("DOMContentLoaded", function() {
    fetchOrders("pending", "pendingOrdersTable");
    fetchOrders("delivering", "deliveringOrdersTable");
    fetchOrders("completed", "completedOrdersTable");
});

function fetchOrders(status, tableId) {
    fetch(`/shipper/orders/${status}`)
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            if (data.success && Array.isArray(data.data)) {
                renderOrders(data.data, tableId);
            } else {
                renderOrders([], tableId); // Hiển thị bảng rỗng nếu không có dữ liệu
            }
        })
        .catch(err => {
            console.error(`Lỗi khi lấy đơn hàng trạng thái ${status}:`, err);
            const tbody = document.querySelector(`#${tableId} tbody`);
            if (tbody) {
                const colCount = tbody.previousElementSibling.querySelectorAll('th').length;
                tbody.innerHTML = `<tr><td colspan="${colCount}" class="text-center text-danger">Có lỗi xảy ra khi tải dữ liệu. Vui lòng thử lại.</td></tr>`;
            }
        });
}

function renderOrders(orders, tableId) {
    const tbody = document.querySelector(`#${tableId} tbody`);
    if (!tbody) return;
    tbody.innerHTML = "";

    if (orders.length === 0) {
        const colCount = tbody.previousElementSibling.querySelectorAll('th').length;
        tbody.innerHTML = `<tr><td colspan="${colCount}" class="text-center">Không có đơn hàng nào.</td></tr>`;
        return;
    }

    try {
        // Render rows
        if (tableId === 'pendingOrdersTable') {
            const rows = orders.map(order => `
                <tr id="order-row-${order.id}">
                    <td>${order.id}</td>
                    <td>${order.customerName}</td>
                    <td>${order.deliveryAddress}</td>
                    <td>${new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(order.totalAmount)}</td>
                    <td>
                        <button class="btn btn-primary" onclick="acceptOrder(${order.id})">Nhận đơn</button>
                    </td>
                </tr>
            `).join('');
            tbody.innerHTML = rows;
        } else if (tableId === 'deliveringOrdersTable') {
            const rows = orders.map(order => {
                const statusClass = getStatusClass(order.status);
                return `
                    <tr>
                        <td>${order.id}</td>
                        <td>${order.address}</td>
                        <td>${order.contactPerson}</td>
                        <td>${order.phone}</td>
                        <td>
                            <span class="badge badge-status ${statusClass}" id="status-badge-${order.id}">${order.status}</span>
                        </td>
                        <td>
                            <button class="btn btn-outline-primary btn-sm d-flex align-items-center justify-content-center" id="update-btn-${order.id}" onclick="showStatusSelect(${order.id})" title="Cập nhật trạng thái" style="width: 38px; height: 38px;">
                                <i class="ri-edit-2-line" style="font-size: 1.5rem;"></i>
                            </button>
                            <div id="status-select-box-${order.id}" style="display:none; margin-top:6px;">
                                <div class="input-group input-group-sm">
                                    <select class="form-select" id="status-select-${order.id}">
                                        <option value="Đã lấy hàng">Đã lấy hàng</option>
                                        <option value="Đang giao">Đang giao</option>
                                        <option value="Đã giao">Đã giao</option>
                                        <option value="Hoàn hàng">Hoàn hàng</option>
                                        <option value="Giao thất bại">Giao thất bại</option>
                                    </select>
                                    <button class="btn btn-success" type="button" onclick="saveStatus(${order.id})">Lưu</button>
                                    <button class="btn btn-secondary" type="button" onclick="hideStatusSelect(${order.id})">Huỷ</button>
                                </div>
                            </div>
                        </td>
                    </tr>
                `;
            }).join('');
            tbody.innerHTML = rows;
        } else if (tableId === 'completedOrdersTable') {
            const rows = orders.map(order => {
                const resultClass = getCompletedStatusClass(order.status);
                const formattedDate = formatDateTime(order.completedAt);
                return `
                    <tr>
                        <td>${order.id}</td>
                        <td>${order.address}</td>
                        <td>${order.contactPerson}</td>
                        <td>${order.phone}</td>
                        <td>
                            <span class="badge ${resultClass}">${getCompletedStatusText(order.status)}</span>
                            ${order.note ? `<br><small>Lý do: ${order.note}</small>` : ''}
                        </td>
                        <td>${formattedDate}</td>
                    </tr>
                `;
            }).join('');
            tbody.innerHTML = rows;
        }
    } catch (error) {
        console.error('Failed to fetch or render orders:', error);
    }
}

// Các hàm hỗ trợ cập nhật trạng thái
function getStatusClass(status) {
    switch(status) {
        case 'Đã lấy hàng': return 'status-layhang';
        case 'Đang giao': return 'status-danggiao';
        case 'Đã giao': return 'status-dagiao';
        case 'Hoàn hàng': return 'status-hoan';
        case 'Giao thất bại': return 'status-thatbai';
        default: return 'bg-secondary';
    }
}

function showStatusSelect(id) {
    document.getElementById('update-btn-' + id).style.display = 'none';
    document.getElementById('status-select-box-' + id).style.display = 'block';
}

function hideStatusSelect(id) {
    // Sử dụng 'd-flex' để nút hiển thị lại đúng như ban đầu
    const button = document.getElementById('update-btn-' + id);
    if(button) button.style.display = 'flex';
    
    const selectBox = document.getElementById('status-select-box-' + id);
    if(selectBox) selectBox.style.display = 'none';
}

function saveStatus(id) {
    const select = document.getElementById('status-select-' + id);
    const badge = document.getElementById('status-badge-' + id);
    const value = select.value;
    
    // Cập nhật giao diện ngay lập tức
    badge.textContent = value;
    badge.className = 'badge badge-status ' + getStatusClass(value);
    hideStatusSelect(id);
    
    // TODO: Gọi API để lưu thay đổi vào database
    console.log(`Đang lưu trạng thái mới cho đơn hàng ${id}: ${value}`);
    // Ví dụ:
    fetch(`/shipper/orders/update-status`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ orderId: id, status: value })
    })
    .then(response => {
        if (response.ok) {
            // Tải lại trang để cập nhật danh sách
            location.reload(); 
        } else {
            alert("Cập nhật thất bại!");
        }
    })
    .catch(err => {
        console.error(`Lỗi khi cập nhật trạng thái đơn hàng ${id}:`, err);
    });
}

// Hàm hỗ trợ cho bảng Đã xong
function getCompletedStatusClass(status) {
    switch(status) {
        case 'COMPLETED': return 'status-layhang'; // Tái sử dụng class màu xanh
        case 'RETURNED': return 'status-hoan';   // Tái sử dụng class màu vàng
        case 'CANCELLED': return 'status-thatbai'; // Tái sử dụng class màu đỏ
        default: return 'bg-secondary';
    }
}

function getCompletedStatusText(status) {
    switch(status) {
        case 'COMPLETED': return 'Thành công';
        case 'RETURNED': return 'Đã hoàn hàng';
        case 'CANCELLED': return 'Đã hủy';
        default: return status;
    }
}

function formatDateTime(dateTimeString) {
    if (!dateTimeString) return "";
    const date = new Date(dateTimeString);
    const day = String(date.getDate()).padStart(2, '0');
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const year = date.getFullYear();
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    return `${hours}:${minutes} ${day}-${month}-${year}`;
} 

