document.addEventListener("DOMContentLoaded", function () {
    const urlParams = new URLSearchParams(window.location.search);
    const productId = urlParams.get("productId");

    if (!productId) return;

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

            
            // ✅ Xử lý tăng/giảm số lượng
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
});
