let relatedProducts = [];
let currentPage = 1;
const itemsPerPage = 4;

// Gọi API và lưu data
function loadRelatedProducts(productId) {
    fetch(`/api/products-dashboard/related?productId=${productId}`)
        .then(res => res.json())
        .then(data => {
            relatedProducts = data;
            renderPage(currentPage);
            setupPagination();
        });
}

// Render 1 trang sản phẩm
function renderPage(page) {
    const start = (page - 1) * itemsPerPage;
    const end = start + itemsPerPage;
    const pageProducts = relatedProducts.slice(start, end);

    const container = document.getElementById('related-product-list');
    container.innerHTML = '';

    document.getElementById('currentPage').textContent = page;
    document.getElementById('totalPage').textContent = Math.ceil(relatedProducts.length / itemsPerPage);

    pageProducts.forEach(p => {
        container.innerHTML += `
        <div class="col-12 col-md-6 col-lg-3 mb-4">
            <div class="product-box-3">
                <div class="product-header">
                    <div class="product-image">
                        <a href="product-image.html?productId=${p.id}">
                            <img src="${p.imageUrl}" class="img-fluid blur-up lazyload" alt="${p.productName}">
                        </a>
                               
                    </div>
                </div>
                <div class="product-footer">
                    <div class="product-detail">
                        <span class="span-name">${p.categoryName}</span>
                        <a href="product-image.html?productId=${p.id}">
                            <h5 class="name">${p.productName}</h5>
                        </a>
                        <h6></h6>
                        <h6 class="unit"></h6>
                        <h5 class="price"><span class="theme-color">${p.wholesalePrice.toLocaleString()}đ</span></h5>
                        <div class="buy-box">
                           <button  onclick="addToWishlist(${p.id})" class="btn btn-md bg-danger wishlist-button text-white w-100">
                             Thêm vào yêu thích
                           </button>
                         </div>
                        <div class="add-to-cart-box bg-white">
                           
                            <div class="cart_qty qty-box">
                                <div class="input-group bg-white">
                                    <button type="button" class="qty-left-minus bg-gray"><i class="fa fa-minus"></i></button>
                                    <input class="form-control input-number qty-input" type="text" value="0">
                                    <button type="button" class="qty-right-plus bg-gray"><i class="fa fa-plus"></i></button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>`;
    });

    if (window.feather) feather.replace();
    document.getElementById('currentPage').textContent = page;

}

function addToWishlist(productId) {
    fetch('/api/wishlist', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ productId })
    })
        .then(res => {
            if (!res.ok) throw new Error("Lỗi khi thêm vào yêu thích");
            return res.json();
        })
        .then(() => {
            alert("Đã thêm vào danh sách yêu thích❤️!");
        })
        .catch(err => {
            console.error("Lỗi:", err);
            alert("Không thể thêm vào yêu thích.");
        });
}

// Phân trang
function setupPagination() {
    document.getElementById('prevPage').onclick = () => {
        if (currentPage > 1) {
            currentPage--;
            renderPage(currentPage);
        }
    };

    document.getElementById('nextPage').onclick = () => {
        if (currentPage < Math.ceil(relatedProducts.length / itemsPerPage)) {
            currentPage++;
            renderPage(currentPage);
        }
    };
}

// Hàm này tự lấy productId từ URL
function getCurrentProductId() {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get('productId');
}

// Gọi khi trang load
const productId = getCurrentProductId();
if (productId) {
    loadRelatedProducts(productId);
}

window.addEventListener('DOMContentLoaded', () => {
    const params = new URLSearchParams(window.location.search);
    const productId = params.get('productId');
    console.log("Đang xem chi tiết sản phẩm:", productId);
});
