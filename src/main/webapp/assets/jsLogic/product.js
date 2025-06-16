document.addEventListener("DOMContentLoaded", function () {
    const urlParams = new URLSearchParams(window.location.search);
    const productId = urlParams.get('id');

    if (productId) {
        fetch(`/api/products/${productId}`)
            
            .then(response => response.json())
            .then(data => {
                if (data.message) {
                    // Nếu trả về lỗi
                    document.getElementById("product-detail").innerText = data.message;
                } else {
                    // Đổ dữ liệu ra giao diện
                    document.getElementById("productName").innerText = data.productName;
                    document.getElementById("retailPrice").innerText = data.retailPrice + " VND";
                    document.getElementById("wholesalePrice").innerText = data.wholesalePrice + " VND";
                    document.getElementById("description").innerText = data.description;
                    document.getElementById("productImage").src = data.imageUrl;
                    document.getElementById("categoryName").innerText = data.categoryName;
                }
            })
            .catch(error => {
                console.error("Lỗi khi gọi API:", error);
            });
    } else {
        document.getElementById("product-detail").innerText = "Không tìm thấy ID sản phẩm";
    }
});
