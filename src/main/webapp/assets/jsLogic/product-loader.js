export async function fetchProducts(params = {}) {
    // 1. Định nghĩa default params
    const defaultParams = {
        page: 0,
        size: 10
    };
    // 2. Gộp default với params truyền vào (params sẽ overwrite default nếu có)
    const mergedParams = { ...defaultParams, ...params };

    // 3. Xây dựng query string
    const query = new URLSearchParams(mergedParams).toString();

    // 4. Tạo URL cuối cùng
    const url = `/api/product?${query}`;

    try {
        const res = await fetch(url);
        if (!res.ok) throw new Error(`Lỗi khi fetch: ${res.status}`);
        const data = await res.json();  // object {products, totalPages, totalItems}
        return data;
    } catch (err) {
        console.error(err);
        // hiển thị lỗi lên UI nếu muốn
    }
}


export function renderProducts(containerId, products) {
    const container = document.getElementById(containerId);
    if (!container) return;
    container.innerHTML = '';  // Xóa cũ

    products.forEach(prod => {
        const wrapper = document.createElement('div');
        wrapper.innerHTML = `
      <div class="product-box product-white-bg wow fadeIn">
        <div class="product-image">
          <a href="product-image.html?productId=${prod.productId}">
            <img src="../assets/images/bg.png" alt="${prod.productName}"
                 class="img-fluid blur-up lazyload">
          </a>
          <ul class="product-option">
            <!-- các icon View/Compare/Wishlist -->
            <li title="View">
              <a href="javascript:void(0)" data-bs-toggle="modal" data-bs-target="#view"
                data-id="${prod.productId}"
                data-name="${prod.productName}"
                data-price="${prod.wholesalePrice}"
                data-category-name="${prod.categoryName}"
                data-desc="${prod.description || ''}"
                >
                <i data-feather="eye"></i>
              </a>
            </li>
            <li title="Compare">
              <a href="compare.html"><i data-feather="refresh-cw"></i></a>
            </li>
            
            <!--Quang Huy-->
             <li title="Wishlist">
             <button class="wishlist-btn" data-product-id="${prod.productId}" style="all:unset;cursor:pointer;">
                      <i data-feather="heart"></i>
               </button>
            </li>
             <!--Quang Huy-->
          </ul>
        </div>
        <div class="product-detail position-relative">
          <a href="product-left-thumbnail.html?productId=${prod.productId}">
            <h6 class="name">${prod.productName}</h6>
          </a>
          <h6 class="sold weight text-content fw-normal">1KG</h6>
          <h6 class="price theme-color">${prod.retailPrice} VNĐ</h6>
          <div class="add-to-cart-btn-2 addtocart_btn">
            <button class="btn addcart-button btn buy-button">
              <i class="fa-solid fa-plus"></i>
            </button>
            <div class="cart_qty qty-box-2">
              <div class="input-group">
                <button type="button" class="qty-left-minus" data-type="minus">
                  <i class="fa fa-minus"></i>
                </button>
                <input class="form-control input-number qty-input"
                       type="text" name="quantity" value="1">
                <button type="button" class="qty-right-plus" data-type="plus">
                  <i class="fa fa-plus"></i>
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    `;
        container.appendChild(wrapper);
    });

    // Re-init Feather icons nếu cần
    if (window.feather) window.feather.replace();
}

export function renderProductsInShopCategory(productsOrObj) {
    const container = document.getElementById("product-list");
    if (!container) return;
    container.innerHTML = '';  // Xóa cũ

    // Nếu truyền vào là object {products, ...} thì lấy .products
    const products = Array.isArray(productsOrObj) ? productsOrObj : (productsOrObj.products || []);

    products.forEach(prod => {
        const wrapper = document.createElement('div');
        wrapper.innerHTML = `
      <div class="product-box-3 h-100 wow fadeInUp">
                                <div class="product-header">
                                    <div class="product-image">
                                        <a href="product-image.html?productId=${prod.productId}">
                                            <img src="../assets/images/cake/product/2.png"
                                                class="img-fluid blur-up lazyload" alt="">
                                        </a>

                                        <ul class="product-option">
                                            <li data-bs-toggle="tooltip" data-bs-placement="top" title="View">
                                                <a href="javascript:void(0)" data-bs-toggle="modal"
                                                    data-bs-target="#view" data-product-id="${prod.productId}">
                                                    <i data-feather="eye"></i>
                                                </a>
                                            </li>

                                            <li data-bs-toggle="tooltip" data-bs-placement="top" title="Compare">
                                                <a href="compare.html">
                                                    <i data-feather="refresh-cw"></i>
                                                </a>
                                            </li>
                                <!--quang huy thêm -->
                                            <li data-bs-toggle="tooltip" data-bs-placement="top" title="Wishlist">
                                                <button class="wishlist-btn" data-product-id="${prod.productId}" style="all:unset;cursor:pointer;">
                                                    <i data-feather="heart"></i>
                                                </button>
                                            </li>
                                <!--quang huy thêm -->
                            
                                        </ul>
                                    </div>
                                </div>
                                <div class="product-footer">
                                    <div class="product-detail">
                                        <span class="span-name">${prod.categoryName}</span>
                                        <a href="product-left-thumbnail.html">
                                            <h5 class="name">${prod.productName}</h5>
                                        </a>
                                        <h6 class="unit">1KG</h6>
                                        <h5 class="price"><span class="theme-color">${prod.wholesalePrice}VNĐ</span> <del>${prod.retailPrice}VNĐ</del>
                                        </h5>
                                        <div class="add-to-cart-box bg-white">
                                            <button class="btn btn-add-cart addcart-button">Add
                                                <span class="add-icon bg-light-gray">
                                                    <i class="fa-solid fa-plus"></i>
                                                </span>
                                            </button>
                                            <div class="cart_qty qty-box">
                                                <div class="input-group bg-white">
                                                    <button type="button" class="qty-left-minus bg-gray"
                                                        data-type="minus" data-field="">
                                                        <i class="fa fa-minus"></i>
                                                    </button>
                                                    <input class="form-control input-number qty-input" type="text"
                                                        name="quantity" value="0">
                                                    <button type="button" class="qty-right-plus bg-gray"
                                                        data-type="plus" data-field="">
                                                        <i class="fa fa-plus"></i>
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
    `;
        container.appendChild(wrapper);
    });

    // Re-init Feather icons nếu cần
    if (window.feather) window.feather.replace();
    attachQuickViewEvents(productsOrObj);
}

export function attachQuickViewEvents(productsOrObj) {
    // Lấy danh sách sản phẩm
    const products = Array.isArray(productsOrObj) ? productsOrObj : (productsOrObj.products || []);
    // Gắn event cho từng nút xem nhanh
    document.querySelectorAll('#product-list .product-option [data-bs-target="#view"]').forEach((btn, idx) => {
        btn.addEventListener('click', function (e) {
            e.preventDefault();
            const prod = products[idx];
            if (!prod) return;
            // Gán dữ liệu vào modal
            const modal = document.getElementById('view');
            if (!modal) return;
            // Ảnh
            const img = modal.querySelector('.slider-image img');
            if (img) img.src = prod.imageUrl || '../assets/images/cake/product/2.png';
            // Tên
            const title = modal.querySelector('.title-name');
            if (title) title.textContent = prod.productName;
            // Giá
            const price = modal.querySelector('.price');
            if (price) price.textContent = prod.wholesalePrice + ' VNĐ';
            // Mô tả
            const desc = modal.querySelector('.product-detail p');
            if (desc) desc.textContent = prod.description || '';
            // Brand (nếu có)
            const brand = modal.querySelector('.brand-list li:nth-child(1) h6');
            if (brand) brand.textContent = prod.brandName || '';
            // Product Code
            const code = modal.querySelector('.brand-list li:nth-child(2) h6');
            if (code) code.textContent = prod.productId || '';
            // Product Type (category)
            const type = modal.querySelector('.brand-list li:nth-child(3) h6');
            if (type) type.textContent = prod.categoryName || '';
        });

    });
}