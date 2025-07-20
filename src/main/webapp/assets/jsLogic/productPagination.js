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
        // Lấy ảnh đầu tiên nếu imageUrl là mảng, nếu không thì để rỗng
        let imgSrc = Array.isArray(p.imageUrl) && p.imageUrl.length > 0 ? p.imageUrl[0] : '';
        container.innerHTML += `
            <tr>
                <td class="product-image">
                    <img src="${imgSrc}" class="img-fluid" alt="">
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
    console.log('Loading products for page:', page); // Debug log

    fetch('/api/products-dashboard')
        .then(res => {
            console.log('Products response status:', res.status); // Debug log
            if (!res.ok) {
                return res.text().then(text => {
                    console.log('Products error response:', text); // Debug log
                    throw new Error('Không thể load danh sách sản phẩm: ' + text);
                });
            }
            return res.json();
        })
        .then(data => {
            console.log('Loaded products:', data); // Debug log
            allProducts = data;
            displayProductsForPage(page);
            updatePagination(data.length);
        })
        .catch(err => {
            console.error('Lỗi khi tải sản phẩm:', err);
            document.getElementById('productList').innerHTML =
                '<tr><td colspan="6" class="text-center text-danger">Không thể tải dữ liệu sản phẩm: ' + err.message + '</td></tr>';
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
        // Lấy ảnh đầu tiên nếu imageUrl là mảng, nếu không thì để rỗng
        let imgSrc = Array.isArray(p.imageUrl) && p.imageUrl.length > 0 ? p.imageUrl[0] : '';
        container.innerHTML += `
            <tr>
                <td class="product-image">
                    <img src="${imgSrc}" class="img-fluid" alt="">
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

    fetch(`/api/products-dashboard/${productId}`, {
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
    console.log('Opening edit modal for product ID:', productId); // Debug log
    console.log('All products:', allProducts); // Debug log
    
    // Tìm sản phẩm trong danh sách
    const product = allProducts.find(p => p.id === productId);
    if (!product) {
        console.error('Product not found:', productId); // Debug log
        showNotification('Không tìm thấy sản phẩm', 'error');
        return;
    }

    console.log('Found product:', product); // Debug log

    // Load danh sách categories trước khi hiển thị modal
    loadCategories().then(() => {
    // Điền dữ liệu vào modal
        const productIdField = document.getElementById('productId');
        const productNameField = document.getElementById('editProductName');
        const wholesalePriceField = document.getElementById('editWholesalePrice');
        const descriptionField = document.getElementById('editDescription');
        const imageUrlField = document.getElementById('editProductImageUrl');
        const categoryNameField = document.getElementById('editCategoryName');
        const categoryIdField = document.getElementById('editCategoryId');
        
        if (!productIdField || !productNameField || !wholesalePriceField || 
            !descriptionField || !imageUrlField || !categoryNameField || !categoryIdField) {
            throw new Error('Không tìm thấy các trường input trong modal');
        }
        
        productIdField.value = product.id;
        productNameField.value = product.productName;
        wholesalePriceField.value = product.wholesalePrice;
        descriptionField.value = product.description || '';
        imageUrlField.value = Array.isArray(product.imageUrl) ? product.imageUrl.join(", ") : '';
        categoryNameField.value = product.categoryName || '';
        categoryIdField.value = product.categoryId;
        
        console.log('Modal fields populated:', {
            id: productIdField.value,
            name: productNameField.value,
            price: wholesalePriceField.value,
            description: descriptionField.value,
            imageUrl: imageUrlField.value,
            categoryName: categoryNameField.value,
            categoryId: categoryIdField.value
        }); // Debug log
        
    const modal = new bootstrap.Modal(document.getElementById('edit-product'));
    modal.show();
    }).catch(error => {
        console.error("Lỗi khi load categories:", error);
        showNotification('Không thể load danh sách loại sản phẩm: ' + error.message, 'error');
    });
}

// Load danh sách categories
function loadCategories() {
    console.log('Loading categories...'); // Debug log
    
    return fetch('/api/categories')
        .then(res => {
            console.log('Categories response status:', res.status); // Debug log
            
            if (!res.ok) {
                return res.text().then(text => {
                    console.log('Categories error response:', text); // Debug log
                    throw new Error('Không thể load danh sách loại sản phẩm: ' + text);
                });
            }
            return res.json();
        })
        .then(categories => {
            console.log('Loaded categories:', categories); // Debug log
            
            const categorySelect = document.getElementById('editCategoryId');
            if (!categorySelect) {
                throw new Error('Không tìm thấy element editCategoryId');
            }
            
            categorySelect.innerHTML = '<option value="">Chọn loại sản phẩm</option>';
            
            if (categories && categories.length > 0) {
                categories.forEach(category => {
                    const option = document.createElement('option');
                    option.value = category.id;
                    option.textContent = category.categoryName;
                    categorySelect.appendChild(option);
                });
            } else {
                console.warn('No categories found');
            }
        })
        .catch(error => {
            console.error('Error loading categories:', error);
            throw error;
        });
}

// Lưu thay đổi edit
function saveEdit() {
    // Validate form
    const form = document.getElementById('editProductForm');
    if (!form.checkValidity()) {
        form.reportValidity();
        return;
    }

    const productId = document.getElementById('productId').value;
    const productName = document.getElementById('editProductName').value.trim();
    const wholesalePrice = parseFloat(document.getElementById('editWholesalePrice').value);
    const description = document.getElementById('editDescription').value.trim();
    const categoryId = parseInt(document.getElementById('editCategoryId').value);
    const imageUrlText = document.getElementById('editProductImageUrl').value.trim();

    // Validate dữ liệu
    if (!productName) {
        alert("Vui lòng nhập tên sản phẩm");
        return;
    }

    if (isNaN(wholesalePrice) || wholesalePrice <= 0) {
        alert("Vui lòng nhập giá bán buôn hợp lệ");
        return;
    }

    if (!categoryId) {
        alert("Vui lòng chọn loại sản phẩm");
        return;
    }

    // Xử lý imageUrl
    let imageUrl = [];
    if (imageUrlText) {
        imageUrl = imageUrlText.split(',').map(url => url.trim()).filter(url => url.length > 0);
    }

    const productData = {
        productName: productName,
        wholesalePrice: wholesalePrice,
        description: description,
        imageUrl: imageUrl,
        categoryId: categoryId
    };

    console.log('Sending product data:', productData); // Debug log

    // Hiển thị loading
    const saveButton = document.querySelector('#edit-product .btn[onclick="saveEdit()"]');
    const originalText = saveButton.textContent;
    saveButton.textContent = 'Đang lưu...';
    saveButton.disabled = true;

    fetch(`/api/products-dashboard/${productId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(productData)
    })
    .then(res => {
        console.log('Response status:', res.status); // Debug log
        
        if (!res.ok) {
            return res.text().then(text => {
                console.log('Error response:', text); // Debug log
                throw new Error(`HTTP ${res.status}: ${text}`);
            });
        }
        return res.json();
    })
    .then(data => {
        console.log('Success response:', data); // Debug log
        
        // Hiển thị thông báo thành công
        showNotification('Cập nhật sản phẩm thành công!', 'success');
        
        // Đóng modal
        const modal = bootstrap.Modal.getInstance(document.getElementById('edit-product'));
        modal.hide();

        // Load lại danh sách sản phẩm từ API
        loadProductsWithPagination(currentPage);
    })
    .catch(err => {
        console.error("Lỗi khi cập nhật sản phẩm:", err);
        
        // Hiển thị lỗi chi tiết hơn
        let errorMessage = 'Có lỗi xảy ra khi cập nhật sản phẩm';
        
        if (err.message.includes('HTTP 400')) {
            errorMessage = 'Dữ liệu không hợp lệ: ' + err.message;
        } else if (err.message.includes('HTTP 404')) {
            errorMessage = 'Không tìm thấy sản phẩm';
        } else if (err.message.includes('HTTP 500')) {
            errorMessage = 'Lỗi server: ' + err.message;
        } else if (err.name === 'TypeError' && err.message.includes('fetch')) {
            errorMessage = 'Không thể kết nối đến server';
        }
        
        showNotification(errorMessage, 'error');
    })
    .finally(() => {
        // Khôi phục button
        saveButton.textContent = originalText;
        saveButton.disabled = false;
    });
}

// Hiển thị thông báo
function showNotification(message, type = 'info') {
    // Tạo toast notification
    const toastContainer = document.getElementById('toast-container') || createToastContainer();
    
    const toast = document.createElement('div');
    toast.className = `toast align-items-center text-white bg-${type === 'success' ? 'success' : type === 'error' ? 'danger' : 'info'} border-0`;
    toast.setAttribute('role', 'alert');
    toast.setAttribute('aria-live', 'assertive');
    toast.setAttribute('aria-atomic', 'true');
    
    toast.innerHTML = `
        <div class="d-flex">
            <div class="toast-body">
                ${message}
            </div>
            <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
        </div>
    `;
    
    toastContainer.appendChild(toast);
    
    const bsToast = new bootstrap.Toast(toast);
    bsToast.show();
    
    // Tự động xóa toast sau khi ẩn
    toast.addEventListener('hidden.bs.toast', () => {
        toast.remove();
    });
}

// Tạo container cho toast notifications
function createToastContainer() {
    const container = document.createElement('div');
    container.id = 'toast-container';
    container.className = 'toast-container position-fixed top-0 end-0 p-3';
    container.style.zIndex = '9999';
    document.body.appendChild(container);
    return container;
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
