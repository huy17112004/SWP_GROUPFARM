let orderCurrentPage = 1;
const orderItemsPerPage = 10; // Cố định 10 đơn hàng/trang
let allOrders = [];
let orderStatusFilter = 'ALL'; // Thêm biến filter trạng thái

function loadOrdersWithPagination(page = 1) {
    orderCurrentPage = page;
    fetch('/api/shipping-stats')
        .then(res => res.json())
        .then(data => {

            console.log("DATA TRẢ VỀ:", data);

            allOrders = data;
            displayOrdersForPage(orderCurrentPage);
            updateOrderPagination(getFilteredOrders().length);
        })
        .catch(err => {
            document.getElementById('all-order-body').innerHTML =
                '<tr><td colspan="4" class="text-center text-danger">Không thể tải dữ liệu đơn hàng</td></tr>';
        });
}

// Hàm lọc đơn hàng theo trạng thái
function getFilteredOrders() {
    if (orderStatusFilter === 'ALL') return allOrders;
    return allOrders.filter(o => o.status === orderStatusFilter);
}

function displayOrdersForPage(page) {
    const container = document.getElementById('all-order-body');
    const filteredOrders = getFilteredOrders();
    const startIndex = (page - 1) * orderItemsPerPage;
    const endIndex = startIndex + orderItemsPerPage;
    const ordersForPage = filteredOrders.slice(startIndex, endIndex);

    container.innerHTML = '';
    if (ordersForPage.length === 0) {
        container.innerHTML = '<tr><td colspan="4" class="text-center">Không có đơn hàng nào</td></tr>';
        return;
    }

    ordersForPage.forEach(o => {
        container.innerHTML += `
            <tr>
                <td>${o.orderId}</td>
                <td>${o.productName}</td>
                <td>${Number(o.totalPrice).toLocaleString('vi-VN')}₫</td>
                <td class="${o.status === 'SHIPPED' ? 'text-success' : 'text-danger'} fw-bold">${o.status}</td>
            </tr>`;
    });
}

function updateOrderPagination(totalItems) {
    const totalPages = Math.ceil(totalItems / orderItemsPerPage);
    const paginationContainer = document.querySelector('#pills-order .custom-pagination ul');

    if (!paginationContainer) {
        console.warn('Pagination container not found for orders');
        return;
    }

    if (totalPages <= 1) {
        paginationContainer.innerHTML = '';
        return;
    }

    let paginationHTML = '';

    // Nút Previous
    paginationHTML += `
        <li class="page-item ${orderCurrentPage === 1 ? 'disabled' : ''}">
            <a class="page-link" href="javascript:void(0)" onclick="changeOrderPage(${orderCurrentPage - 1})">
                <i class="fa-solid fa-angles-left"></i>
            </a>
        </li>`;

    // Các số trang
    for (let i = 1; i <= totalPages; i++) {
        paginationHTML += `
            <li class="page-item ${i === orderCurrentPage ? 'active' : ''}">
                <a class="page-link" href="javascript:void(0)" onclick="changeOrderPage(${i})">${i}</a>
            </li>`;
    }

    // Nút Next
    paginationHTML += `
        <li class="page-item ${orderCurrentPage === totalPages ? 'disabled' : ''}">
            <a class="page-link" href="javascript:void(0)" onclick="changeOrderPage(${orderCurrentPage + 1})">
                <i class="fa-solid fa-angles-right"></i>
            </a>
        </li>`;

    paginationContainer.innerHTML = paginationHTML;
}

function changeOrderPage(page) {
    const totalPages = Math.ceil(getFilteredOrders().length / orderItemsPerPage);
    if (page < 1 || page > totalPages) return;

    orderCurrentPage = page;
    displayOrdersForPage(orderCurrentPage);
    updateOrderPagination(getFilteredOrders().length);
}

// Hàm set filter trạng thái và cập nhật lại bảng
function setOrderStatusFilter(status) {
    orderStatusFilter = status;
    orderCurrentPage = 1;
    displayOrdersForPage(orderCurrentPage);
    updateOrderPagination(getFilteredOrders().length);
}

// Để gọi từ HTML
window.setOrderStatusFilter = setOrderStatusFilter;

document.addEventListener('DOMContentLoaded', function () {
    loadOrdersWithPagination(1);
// Lắng nghe sự kiện thay đổi của select filter nếu có
    const filterSelect = document.getElementById('order-status-filter-select');
    if (filterSelect) {
        filterSelect.addEventListener('change', function(e) {
            setOrderStatusFilter(e.target.value);
        });
    }
});

window.changeOrderPage = changeOrderPage;
