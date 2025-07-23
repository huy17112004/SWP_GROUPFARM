// Constants
const API_URL = '/api/top-products';

// Function to format currency in VND
function formatCurrency(amount) {
    return new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND'
    }).format(amount);
}

// Function to create a product card
function createProductCard(product, index) {
    return `
        <div class="col-sm-6 col-xxl-3 col-ed-4">
            <div class="best-selling-box">
                <div class="best-selling-image">
                    <img src="${product.imageUrl || '/assets/images/placeholder.png'}" 
                         class="img-fluid" 
                         alt="${product.productName}">
                </div>
                <div class="best-selling-contain">
                    <h5 class="rank">#${index + 1}</h5>
                    <h5 class="name">${product.productName}</h5>
                    <h6 class="price">Giá: ${formatCurrency(product.price)}</h6>
                    <div class="best-selling-contain">
                        <h6 class="sold">Đã bán: ${product.totalQuantitySold}</h6>
                        <h6 class="revenue">Doanh thu: ${formatCurrency(product.totalRevenue)}</h6>
                    </div>
                </div>
            </div>
        </div>
    `;
}

// Function to render the top products table
function renderTopProductsTable(products) {
    const tbody = document.getElementById('top-product-body');
    tbody.innerHTML = '';

    if (!products || products.length === 0) {
        tbody.innerHTML = '<tr><td colspan="5" class="text-center">Không có dữ liệu</td></tr>';
        return;
    }

    products.forEach((product, index) => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${index + 1}</td>
            <td>${product.productName}</td>
            <td>
                <img src="${product.imageUrl || '/assets/images/placeholder.png'}" 
                     class="img-fluid" 
                     alt="${product.productName}"
                     style="max-width: 50px;">
            </td>
            <td>${formatCurrency(product.price)}</td>
            <td>${product.totalQuantitySold}</td>
        `;
        tbody.appendChild(row);
    });
}

// Function to render product cards
function renderProductCards(products) {
    const cardsContainer = document.getElementById('topProductsCards');
    cardsContainer.innerHTML = '';

    if (!products || products.length === 0) {
        cardsContainer.innerHTML = '<div class="col-12 text-center">Không có dữ liệu</div>';
        return;
    }

    products.forEach((product, index) => {
        cardsContainer.innerHTML += createProductCard(product, index);
    });
}

// Main function to load top products
async function loadTopProducts() {
    try {
        const limit = document.getElementById('limitSelect').value;
        const sortBy = document.getElementById('sortBy').value;

        const response = await fetch(`${API_URL}?limit=${limit}&sortBy=${sortBy}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const data = await response.json();
        
        // Render both table and cards
        renderTopProductsTable(data);
        renderProductCards(data);

    } catch (error) {
        console.error('Error loading top products:', error);
        // Show error notification
        showNotification('Lỗi khi tải dữ liệu', 'danger');
    }
}

// Function to show notifications
function showNotification(message, type = 'info') {
    $.notify({
        message: message
    }, {
        type: type,
        allow_dismiss: true,
        newest_on_top: false,
        mouse_over: false,
        showProgressbar: false,
        spacing: 10,
        timer: 2000,
        placement: {
            from: 'top',
            align: 'right'
        },
        offset: {
            x: 30,
            y: 30
        },
        delay: 1000,
        z_index: 10000,
        animate: {
            enter: 'animated fadeIn',
            exit: 'animated fadeOut'
        }
    });
}

// Initialize when document is ready
document.addEventListener('DOMContentLoaded', function() {
    loadTopProducts();

    // Add event listeners to filters
    document.getElementById('limitSelect').addEventListener('change', loadTopProducts);
    document.getElementById('sortBy').addEventListener('change', loadTopProducts);
});