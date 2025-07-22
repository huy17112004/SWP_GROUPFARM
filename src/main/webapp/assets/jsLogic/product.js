 document.addEventListener("DOMContentLoaded", function () {
    const urlParams = new URLSearchParams(window.location.search);
    const productId = urlParams.get("productId");

    if (productId) {
    fetch(`/api/product-detail/${productId}`)
        .then(res => res.json())
        .then(data => {
            // Hiển thị tên sản phẩm
            document.getElementById("productName").innerText = data.productName;

            // Format tiền Việt Nam
            const formatPrice = price => parseFloat(price).toLocaleString('vi-VN') + ' VND';

            // Hiển thị giá bán sỉ
            document.getElementById("wholesalePrice").innerText = formatPrice(data.wholesalePrice);


            // Hiển thị mô tả, danh mục, hình ảnh
            document.getElementById("description").innerText = data.description;
            document.getElementById("categoryName").innerText = data.categoryName;
            document.getElementById("productImage").src = data.imageUrl;

            // Breadcrumb
            document.getElementById("breadcrumbTitle").innerText = data.productName;
            document.getElementById("breadcrumbName").innerText = data.productName;


            //  Xử lý tăng/giảm số lượng
            const quantityInput = document.getElementById("quantityInput");
            let currentQty = parseInt(quantityInput.value) || 1;

            document.querySelector(".qty-left-minus").addEventListener("click", () => {
                if (currentQty > 1) {
                    currentQty--;
                    quantityInput.value = currentQty;
                }
            });

            document.querySelector(".qty-right-plus").addEventListener("click", () => {
                currentQty++;
                quantityInput.value = currentQty;
            });

            quantityInput.addEventListener("input", () => {
                const val = parseInt(quantityInput.value);
                if (!isNaN(val) && val > 0) {
                    currentQty = val;
                } else {
                    quantityInput.value = currentQty;
                }
            });
        })

        .catch(err => {
            console.error("Lỗi khi lấy chi tiết sản phẩm:", err);
        });
        //  Gọi thêm API load danh sách ảnh theo productId
        fetch(`/api/product-images?productId=${productId}`)
            .then(res => res.json())
            .then(images => {
                const mainSlider = document.getElementById("main-slider");
                const thumbSlider = document.getElementById("thumb-slider");

                if (!mainSlider || !thumbSlider || images.length === 0) return;

                mainSlider.innerHTML = "";
                thumbSlider.innerHTML = "";

                images.forEach((url, index) => {
                    mainSlider.innerHTML += `
                        <div>
                            <div class="slider-image">
                                <img src="${url}" data-zoom-image="${url}"
                                     class="img-fluid image_zoom_cls-${index} blur-up lazyload" alt="Ảnh sản phẩm">
                            </div>
                        </div>
                    `;

                    thumbSlider.innerHTML += `
                        <div>
                            <div class="sidebar-image">
                                <img src="${url}" class="img-fluid blur-up lazyload" alt="thumb">
                            </div>
                        </div>
                    `;
                });

                // (Tùy bạn: nếu dùng slick slider thì gọi lại .slick() ở đây)
            })
            .catch(err => {
                console.error("Lỗi khi tải ảnh sản phẩm:", err);
            });
    }



        function loadTotalProducts() {
        fetch('/api/stats?action=count')
            .then(response => {
                if (!response.ok) throw new Error('Không lấy được số lượng sản phẩm');
                return response.json();
            })
            .then(data => {
                const target = document.getElementById('totalProducts');
                if (target) {
                    target.innerText = data.total;
                }
            })
            .catch(error => {
                console.error('Lỗi khi tải số lượng sản phẩm:', error);
                const target = document.getElementById('totalProducts');
                if (target) {
                    target.innerText = "N/A";
                }
            });
    }


    function loadAdminProductList() {
        fetch('/api/stats')
            .then(res => res.json())
            .then(data => {
                const container = document.getElementById('productList');
                container.innerHTML = '';
                data.forEach(p => {
                    container.innerHTML += `
                    <tr>
                        <td class="product-image">
                            <img src="${p.imageUrl}" class="img-fluid" alt="">
                        </td>
                        <td>
                            <h6>${p.productName}</h6>
                        </td>
                        <td>${p.description}</td>
                        <td>${p.categoryName}</td>
                        <td>
                            <h6 class="theme-color fw-bold">${Number(p.wholesalePrice).toLocaleString('vi-VN')}₫</h6>
                        </td>
                     
                        <td class="edit-delete">
                            <i data-feather="edit" class="edit" onclick="showEditModal(${p.id})"></i>
                            <i data-feather="trash-2" class="delete" onclick="deleteProduct(${p.id})"></i>


                        </td>
                    </tr>
                `;
                });

                if (window.feather) {
                    feather.replace();
                }
            })
            .catch(err => {
                console.error('Lỗi khi tải danh sách sản phẩm:', err);
            });
    }

    window.deleteProduct = function (productId) {
        if (!confirm("Bạn có chắc chắn muốn xoá sản phẩm này không?")) return;

        fetch(`/api/products/${productId}`, {
            method: 'DELETE'
        })
            .then(res => {
                if (!res.ok) throw new Error("Lỗi khi xoá sản phẩm");
                alert("Xoá thành công");
                loadAdminProductList(); // Hoặc load lại danh sách
            })
            .catch(err => console.error("Lỗi khi xoá sản phẩm:", err));
    };
         <!--UPDATE-->
    let currentCategoryId = null; // Biến lưu categoryId ẩn

    window.showEditModal = function(productId) {
        fetch(`/api/products/${productId}`)
            .then(res => res.json())
            .then(data => {
                document.getElementById("productId").value = data.id;
                document.getElementById("editProductName").value = data.productName;
                document.getElementById("editWholesalePrice").value = data.wholesalePrice;
                document.getElementById("editDescription").value = data.description;
                document.getElementById("editProductImageUrl").value = data.imageUrl;
                document.getElementById("editCategoryName").value = data.categoryName;

                currentCategoryId = data.categoryId;

                const modal = new bootstrap.Modal(document.getElementById("edit-product"));
                modal.show();
            })
            .catch(err => {
                console.error("Lỗi khi tải dữ liệu sản phẩm:", err);
                alert("Không thể tải dữ liệu sản phẩm.");
            });
    }

    window.saveEdit = function () {
        const id = document.getElementById("productId").value;

        const updatedData = {
            productName: document.getElementById("editProductName").value,
            wholesalePrice: parseFloat(document.getElementById("editWholesalePrice").value),
            description: document.getElementById("editDescription").value,
            imageUrl: document.getElementById("editProductImageUrl").value,
            categoryId: currentCategoryId // rất quan trọng
        };

        fetch(`/api/products/${id}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(updatedData)
        })
            .then(res => {
                if (!res.ok) throw new Error("Lỗi khi cập nhật sản phẩm");
                return res.json();
            })
            .then(() => {
                alert("Cập nhật thành công!");
                const modal = bootstrap.Modal.getInstance(document.getElementById("edit-product"));
                modal.hide();
                loadAdminProductList(); // reload danh sách
            })
            .catch(err => {
                console.error("Lỗi khi cập nhật:", err);
                alert("Cập nhật thất bại!");
            });
    }

    loadTotalProducts();
    loadAdminProductList();

});
