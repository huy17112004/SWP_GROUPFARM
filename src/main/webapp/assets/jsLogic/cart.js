

//  Lấy productId từ URL
function getProductIdFromURL() {
    const params = new URLSearchParams(window.location.search);
    return params.get("productId");
}

// Gửi yêu cầu thêm sản phẩm vào giỏ hàng
function addToCart() {
    const productId = getProductIdFromURL();
    const quantityInput = document.getElementById('quantityInput');
    const quantity = parseInt(quantityInput?.value || "0");

    if (!productId || isNaN(quantity) || quantity <= 0) {
        alert('Vui lòng chọn số lượng hợp lệ và đảm bảo có productId.');
        return;
    }

    const cartItem = {
        userId: 1, // Giả lập userId
        productId: parseInt(productId),
        quantity: quantity
    };

    fetch('/api/cart', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(cartItem)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Lỗi khi thêm vào giỏ hàng');
            }
            return response.json();
        })
        .then(data => {
            alert('Đã thêm vào giỏ hàng!');
            window.location.href = 'cart.html';
        })
        .catch(error => {
            alert('Lỗi: ' + error.message);
        });
}

// Gắn sự kiện sau khi DOM đã load
document.addEventListener("DOMContentLoaded", () => {
    // Nút tăng
    const plusBtn = document.querySelector(".qty-right-plus");
    if (plusBtn) {
        plusBtn.addEventListener("click", () => {
            const input = document.getElementById("quantityInput");
            let value = parseInt(input.value || "1");
            input.value = value + 1;
        });
    }

    // Nút giảm
    const minusBtn = document.querySelector(".qty-left-minus");
    if (minusBtn) {
        minusBtn.addEventListener("click", () => {
            const input = document.getElementById("quantityInput");
            let value = parseInt(input.value || "1");
            if (value > 1) input.value = value - 1;
        });
    }

    // Nút Add To Cart
    const addToCartBtn = document.getElementById("addToCartButton");
    if (addToCartBtn) {
        addToCartBtn.addEventListener("click", addToCart);
    }
});

    //Hiển thị danh sách sản phẩm trong giỏ hàng

    function loadCart() {
    fetch("/api/cart", {
        method: "GET",
        headers: {
            "Content-Type": "application/json"
        }
    })
        .then(res => {
            if (!res.ok) throw new Error("Không thể lấy dữ liệu giỏ hàng.");
            return res.json();
        })
        .then(cartItems => {
            const tbody = document.getElementById("cart-table-body");
            if (!tbody) return;

            tbody.innerHTML = "";

            cartItems.forEach(item => {
                const row = `
                <tr>
                    <td><img src="${item.imageUrl || 'default.jpg'}" alt="image" width="60"/></td>
                    <td>${item.productName}</td> 
                    <td>${item.wholesalePrice.toLocaleString('vi-VN')} VND</td>
                    <td>${item.quantity}</td>
                    <td> 
                        <button class="btn btn-danger btn-sm" onclick="removeFromCart(${item.productId})">Remove</button>
                    </td>
                </tr>
            `;
                tbody.innerHTML += row;
            });
        })
        .catch(err => {
            console.error(err);
            alert("Lỗi khi tải giỏ hàng: " + err.message);
        });
}

    // Gửi yêu cầu xóa sản phẩm khỏi giỏ hàng
    function removeFromCart(productId) {
    fetch(`/api/cart/${productId}`, {
        method: 'DELETE'
    })
        .then(res => {
            if (!res.ok) throw new Error("Không thể xóa sản phẩm.");
            return res.json();
        })
        .then(data => {
            alert(data.message || "Xóa thành công");
            location.reload();
        })
        .catch(err => alert("Lỗi khi xóa: " + err.message));
}


    // Tự động gọi loadCart khi trang giỏ hàng sẵn sàng
    document.addEventListener("DOMContentLoaded", function () {
    if (document.getElementById("cart-table-body")) {
    loadCart();
}
});










