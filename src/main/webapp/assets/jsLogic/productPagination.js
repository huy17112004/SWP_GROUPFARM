// Product Pagination Logic
let currentPage = 1;
let itemsPerPage = 10; // Số sản phẩm trên mỗi trang
let allProducts = []; // Lưu tất cả sản phẩm

// Khởi tạo event listeners
function initializePaginationControls() {
    const itemsPerPageSelect = document.getElementById('itemsPerPage');
    if (itemsPerPageSelect) {
        itemsPerPageSelect.addEventListener('change', function() {
            itemsPerPage = parseInt(this.value);
            currentPage = 1; // Reset về trang đầu
            const searchTerm = document.getElementById('searchProduct')?.value.toLowerCase().trim();
            if (searchTerm) {
                filterAndDisplayProducts(searchTerm);
            } else {
                displayProductsForPage(currentPage);
                updatePagination(allProducts.length);
            }
        });
    }

    // Tìm kiếm sản phẩm
    const searchInput = document.getElementById('searchProduct');
    if (searchInput) {
        let searchTimeout;
        searchInput.addEventListener('input', function() {
            clearTimeout(searchTimeout);
            searchTimeout = setTimeout(() => {
                const searchTerm = this.value.toLowerCase().trim();
                filterAndDisplayProducts(searchTerm);
            }, 300); // Delay 300ms để tránh gọi quá nhiều
        });
    }
}

// Lọc và hiển thị sản phẩm theo từ khóa tìm kiếm
function filterAndDisplayProducts(searchTerm) {
    if (!searchTerm) {
        // Nếu không có từ khóa, hiển thị tất cả
        displayProductsForPage(currentPage);
        updatePagination(allProducts.length);
        return;
    }

    // Lọc sản phẩm theo từ khóa
    const filteredProducts = allProducts.filter(product =>
        product.productName.toLowerCase().includes(searchTerm) ||
        (product.description && product.description.toLowerCase().includes(searchTerm)) ||
        (product.categoryName && product.categoryName.toLowerCase().includes(searchTerm))
    );

    // Hiển thị sản phẩm đã lọc
    displayFilteredProducts(filteredProducts);
    updatePagination(filteredProducts.length);
}

// Hiển thị sản phẩm đã lọc
function displayFilteredProducts(filteredProducts) {
    const container = document.getElementById('productList');
    const startIndex = (currentPage - 1) * itemsPerPage;
    const endIndex = startIndex + itemsPerPage;
    const productsForPage = filteredProducts.slice(startIndex, endIndex);

    container.innerHTML = '';

    if (productsForPage.length === 0) {
        container.innerHTML = '<tr><td colspan="6" class="text-center">Không tìm thấy sản phẩm nào</td></tr>';
        updateProductInfo(0, 0, 0);
        return;
    }

    productsForPage.forEach(p => {
        container.innerHTML += `
            <tr>
                <td class="product-image">
                    <img src="${p.imageUrl}" class="img-fluid" alt="">
                </td>
                <td><h6>${p.productName}</h6></td>
                <td>${p.description}</td>
                <td>${p.categoryName}</td>
                <td><h6 class="theme-color fw-bold">${Number(p.wholesalePrice).toLocaleString('vi-VN')}₫</h6></td>
                <td class="edit-delete">
                    <i data-feather="edit" class="edit" onclick="showEditModal(${p.id})"></i>
                    <i data-feather="trash-2" class="delete" onclick="deleteProduct(${p.id})"></i>
                </td>
            </tr>`;
    });

    // Cập nhật thông tin hiển thị
    updateProductInfo(startIndex + 1, endIndex, filteredProducts.length);

    // Refresh feather icons
    if (window.feather) feather.replace();
}

// Load sản phẩm với phân trang
function loadProductsWithPagination(page = 1) {
    currentPage = page;

    fetch('/api/products')
        .then(res => res.json())
        .then(data => {
            allProducts = data;
            displayProductsForPage(page);
            updatePagination(data.length);
        })
        .catch(err => {
            console.error('Lỗi khi tải sản phẩm:', err);
            document.getElementById('productList').innerHTML =
                '<tr><td colspan="6" class="text-center text-danger">Không thể tải dữ liệu sản phẩm</td></tr>';
        });
}

// Hiển thị sản phẩm cho trang cụ thể
function displayProductsForPage(page) {
    const container = document.getElementById('productList');
    const startIndex = (page - 1) * itemsPerPage;
    const endIndex = startIndex + itemsPerPage;
    const productsForPage = allProducts.slice(startIndex, endIndex);

    container.innerHTML = '';

    if (productsForPage.length === 0) {
        container.innerHTML = '<tr><td colspan="6" class="text-center">Không có sản phẩm nào</td></tr>';
        updateProductInfo(0, 0, 0);
        return;
    }

    productsForPage.forEach(p => {
        container.innerHTML += `
            <tr>
                <td class="product-image">
                    <img src="${p.imageUrl}" class="img-fluid" alt="">
                </td>
                <td><h6>${p.productName}</h6></td>
                <td>${p.description}</td>
                <td>${p.categoryName}</td>
                <td><h6 class="theme-color fw-bold">${Number(p.wholesalePrice).toLocaleString('vi-VN')}₫</h6></td>
                <td class="edit-delete">
                    <i data-feather="edit" class="edit" onclick="showEditModal(${p.id})"></i>
                    <i data-feather="trash-2" class="delete" onclick="deleteProduct(${p.id})"></i>
                </td>
            </tr>`;
    });

    // Cập nhật thông tin hiển thị
    updateProductInfo(startIndex + 1, endIndex, allProducts.length);

    // Refresh feather icons
    if (window.feather) feather.replace();
}

// Cập nhật thông tin hiển thị sản phẩm
function updateProductInfo(start, end, total) {
    const productInfo = document.getElementById('productInfo');
    if (productInfo) {
        if (total === 0) {
            productInfo.textContent = 'Không có sản phẩm nào';
        } else {
            productInfo.textContent = `Hiển thị ${start}-${Math.min(end, total)} của ${total} sản phẩm`;
        }
    }
}

// Cập nhật phân trang
function updatePagination(totalItems) {
    const totalPages = Math.ceil(totalItems / itemsPerPage);
    const paginationContainer = document.querySelector('#pills-product .custom-pagination ul');

    if (totalPages <= 1) {
        paginationContainer.innerHTML = '';
        return;
    }

    let paginationHTML = '';

    // Nút Previous
    const prevDisabled = currentPage === 1 ? 'disabled' : '';
    paginationHTML += `
        <li class="page-item ${prevDisabled}">
            <a class="page-link" href="javascript:void(0)" onclick="changePage(${currentPage - 1})" ${prevDisabled ? 'tabindex="-1"' : ''}>
                <i class="fa-solid fa-angles-left"></i>
            </a>
        </li>
    `;

    // Các số trang
    const maxVisiblePages = 5;
    let startPage = Math.max(1, currentPage - Math.floor(maxVisiblePages / 2));
    let endPage = Math.min(totalPages, startPage + maxVisiblePages - 1);

    if (endPage - startPage + 1 < maxVisiblePages) {
        startPage = Math.max(1, endPage - maxVisiblePages + 1);
    }

    // Hiển thị trang đầu nếu cần
    if (startPage > 1) {
        paginationHTML += `
            <li class="page-item">
                <a class="page-link" href="javascript:void(0)" onclick="changePage(1)">1</a>
            </li>
        `;
        if (startPage > 2) {
            paginationHTML += `
                <li class="page-item disabled">
                    <a class="page-link" href="javascript:void(0)">...</a>
                </li>
            `;
        }
    }

    // Các trang chính
    for (let i = startPage; i <= endPage; i++) {
        const activeClass = i === currentPage ? 'active' : '';
        paginationHTML += `
            <li class="page-item ${activeClass}">
                <a class="page-link" href="javascript:void(0)" onclick="changePage(${i})">${i}</a>
            </li>
        `;
    }

    // Hiển thị trang cuối nếu cần
    if (endPage < totalPages) {
        if (endPage < totalPages - 1) {
            paginationHTML += `
                <li class="page-item disabled">
                    <a class="page-link" href="javascript:void(0)">...</a>
                </li>
            `;
        }
        paginationHTML += `
            <li class="page-item">
                <a class="page-link" href="javascript:void(0)" onclick="changePage(${totalPages})">${totalPages}</a>
            </li>
        `;
    }

    // Nút Next
    const nextDisabled = currentPage === totalPages ? 'disabled' : '';
    paginationHTML += `
        <li class="page-item ${nextDisabled}">
            <a class="page-link" href="javascript:void(0)" onclick="changePage(${currentPage + 1})" ${nextDisabled ? 'tabindex="-1"' : ''}>
                <i class="fa-solid fa-angles-right"></i>
            </a>
        </li>
    `;

    paginationContainer.innerHTML = paginationHTML;
}

// Chuyển trang
function changePage(page) {
    const searchTerm = document.getElementById('searchProduct')?.value.toLowerCase().trim();

    if (searchTerm) {
        // Nếu đang tìm kiếm, sử dụng danh sách đã lọc
        const filteredProducts = allProducts.filter(product =>
            product.productName.toLowerCase().includes(searchTerm) ||
            (product.description && product.description.toLowerCase().includes(searchTerm)) ||
            (product.categoryName && product.categoryName.toLowerCase().includes(searchTerm))
        );

        if (page < 1 || page > Math.ceil(filteredProducts.length / itemsPerPage)) {
            return;
        }
        currentPage = page;
        displayFilteredProducts(filteredProducts);
        updatePagination(filteredProducts.length);
    } else {
        // Nếu không tìm kiếm, sử dụng tất cả sản phẩm
        if (page < 1 || page > Math.ceil(allProducts.length / itemsPerPage)) {
            return;
        }
        currentPage = page;
        displayProductsForPage(page);
        updatePagination(allProducts.length);
    }
}

// Xóa sản phẩm
function deleteProduct(productId) {
    if (!confirm("Bạn có chắc chắn muốn xoá sản phẩm này không?")) return;

    fetch(`/api/products/${productId}`, {
        method: 'DELETE'
    })
        .then(res => {
            if (!res.ok) throw new Error("Lỗi khi xoá sản phẩm");
            alert("Xoá thành công");
            loadProductsWithPagination(currentPage); // Load lại trang hiện tại
        })
        .catch(err => {
            console.error("Lỗi khi xoá sản phẩm:", err);
            alert("Có lỗi xảy ra khi xoá sản phẩm");
        });
}

// Hiển thị modal edit
function showEditModal(productId) {
    // Tìm sản phẩm trong danh sách
    const product = allProducts.find(p => p.id === productId);
    if (!product) {
        alert("Không tìm thấy sản phẩm");
        return;
    }

    // Điền dữ liệu vào modal
    document.getElementById('productId').value = product.id;
    document.getElementById('editProductName').value = product.productName;
    document.getElementById('editWholesalePrice').value = product.wholesalePrice;
    document.getElementById('editDescription').value = product.description || '';
    document.getElementById('editProductImageUrl').value = product.imageUrl || '';
    document.getElementById('editCategoryName').value = product.categoryName || '';
    document.getElementById('editCategoryId').value = product.categoryId;
    const modal = new bootstrap.Modal(document.getElementById('edit-product'));
    modal.show();
}

// Lưu thay đổi edit
function saveEdit() {
    const productId = document.getElementById('productId').value;
    const productData = {
        productName: document.getElementById('editProductName').value,
        wholesalePrice: parseFloat(document.getElementById('editWholesalePrice').value),
        description: document.getElementById('editDescription').value,
        imageUrl: document.getElementById('editProductImageUrl').value,
        categoryId: parseInt(document.getElementById('editCategoryId').value)// <-- thêm dòng này
    };

    fetch(`/api/products/${productId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(productData)
    })
        .then(res => {
            if (!res.ok) throw new Error("Lỗi khi cập nhật sản phẩm");
            alert("Cập nhật thành công");

            // Đóng modal
            const modal = bootstrap.Modal.getInstance(document.getElementById('edit-product'));
            modal.hide();

            // Load lại trang hiện tại
            loadProductsWithPagination(currentPage);
        })
        .catch(err => {
            console.error("Lỗi khi cập nhật sản phẩm:", err);
            alert("Có lỗi xảy ra khi cập nhật sản phẩm");
        });
}


// Khởi tạo khi DOM load xong
document.addEventListener('DOMContentLoaded', function() {
    // Khởi tạo controls
    initializePaginationControls();

    // Load sản phẩm trang đầu tiên
    loadProductsWithPagination(1);

});

// Export functions để sử dụng trong HTML
window.loadProductsWithPagination = loadProductsWithPagination;
window.changePage = changePage;
window.deleteProduct = deleteProduct;
window.showEditModal = showEditModal;
window.saveEdit = saveEdit;
