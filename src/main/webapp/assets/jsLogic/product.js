document.addEventListener("DOMContentLoaded", function () {
    const urlParams = new URLSearchParams(window.location.search);
    const productId = urlParams.get("id");

    if (productId) {
        // GỌI API CHI TIẾT SẢN PHẨM
        fetch(`/api/product-detail/${productId}`)
            .then(response => response.json())
            .then(data => {
                if (data.message) {
                    document.getElementById("product-detail").innerText = data.message;
                } else {
                    document.getElementById("productName").innerText = data.productName;
                    document.getElementById("retailPrice").innerText = data.retailPrice + " VND";
                    document.getElementById("wholesalePrice").innerText = data.wholesalePrice + " VND";
                    document.getElementById("description").innerText = data.description;
                    document.getElementById("productImage").src = data.imageUrl;
                    document.getElementById("categoryName").innerText = data.categoryName;
                }
            })
            .catch(error => {
                console.error("Lỗi khi gọi API chi tiết sản phẩm:", error);
            });

    } else {
        // GỌI API DANH SÁCH SẢN PHẨM
        fetch(`/api/product`)
            .then(response => response.json())
            .then(data => {
                const container = document.getElementById("product-list");
                const template = document.getElementById("product-template").content;

                data.forEach(product => {
                    const clone = document.importNode(template, true);
                    clone.querySelector(".productName").innerText = product.productName;
                    clone.querySelector(".retailPrice").innerText = product.retailPrice + " VND";
                    clone.querySelector(".productImage").src = product.imageUrl;
                    clone.querySelector(".productLink").href = "product-detail.jsp?id=" + product.id;
                    container.appendChild(clone);
                });

                if (window.feather) {
                    feather.replace();
                }
            })
            .catch(error => {
                console.error("Lỗi khi gọi API danh sách sản phẩm:", error);
            });
    }
});
