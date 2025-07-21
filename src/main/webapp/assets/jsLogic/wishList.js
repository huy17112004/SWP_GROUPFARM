function loadWishlist() {
    fetch("/api/wishlist", {
        method: "GET",
        headers: {
            "Content-Type": "application/json"
        }
    })
        .then(res => {
            if (!res.ok) throw new Error("Không thể lấy dữ liệu wishlist.");
            return res.json();
        })
        .then(wishlistItems => {
            const tbody = document.getElementById("wishlist-table-body");
            if (!tbody) return;

            tbody.innerHTML = "";

            if (wishlistItems.length === 0) {
                tbody.innerHTML = `<tr><td colspan="5" class="text-center">Danh sách yêu thích trống.</td></tr>`;
                return;
            }

            wishlistItems.forEach(item => {
                const row = `
                    <tr>
                        <td><img src="${item.productImage || 'default.jpg'}" alt="image" width="60"/></td>
                        <td>${item.productName}</td>
                        <td>${Number(item.productPrice).toLocaleString('vi-VN')} VND</td>
                        <td>
                            <div class="wishlist-actions">
                                <a href="product-image.html?productId=${item.productId}" class="btn btn-info btn-sm action-btn">Xem chi tiết</a>
                                 <button class="btn btn-success btn-sm action-btn add-to-cart-btn"  onclick="addToCartFromWishlist(${item.productId})">Thêm vào giỏ hàng</button>
                                <button class="btn btn-danger btn-sm action-btn" onclick="removeFromWishlist(${item.productId})">Xóa</button>
                            </div>
                        </td>
                    </tr>
                `;
                tbody.innerHTML += row;
            });

            // Gắn lại sự kiện cho các nút thêm vào giỏ
            document.querySelectorAll('.add-to-cart-btn').forEach(button => {
                button.addEventListener('click', () => {
                    const productId = button.getAttribute('data-product-id');
                    addToCartFromWishlist(parseInt(productId));
                });
            });
        })
        .catch(err => {
            console.error(err);
            alert("Lỗi khi tải wishlist: " + err.message);
        });
}

// Gọi khi DOM đã sẵn sàng
document.addEventListener("DOMContentLoaded", function () {
    if (document.getElementById("wishlist-table-body")) {
        loadWishlist();
    }
});

// Hàm thêm vào giỏ hàng
function addToCartFromWishlist(productId) {
    fetch(`/api/cart`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ productId: productId, quantity: 1 })
    })
        .then(res => {
            alert("✅ Đã thêm vào giỏ hàng!");
            window.location.href = 'cart.html';
        })
        .catch(err => {
            console.error(err);
            alert("Lỗi khi thêm vào giỏ hàng: " + err.message);
        });
}


// Gửi yêu cầu thêm sản phẩm vào wishlist
function addToWishlist() {
    const productId = getProductIdFromURL();

    if (!productId) {
        alert('Không tìm thấy productId!');
        return;
    }

    // Nếu backend nhận JSON:
    const wishlistItem = {
        productId: parseInt(productId)
        // Nếu backend cần userId, có thể thêm userId: 1 (giả lập)
    };

    fetch('/api/wishlist', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(wishlistItem)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Lỗi khi thêm vào wishlist');
            }
            return response.json();
        })
        .then(data => {
            alert('❤️ Đã thêm vào danh yêu thích!');
            window.location.href = 'wishlist.html';
        })
        .catch(error => {
            alert('Lỗi: ' + error.message);
        });
}
function removeFromWishlist(productId) {
    fetch(`/api/wishlist/${productId}`, {
        method: 'DELETE'
    })

        .then(async res => {
            // Lấy nội dung trả về (dù là lỗi hay thành công)
            const data = await res.json().catch(() => ({}));
            if (!res.ok) {
                // Nếu backend trả về lỗi, hiển thị chi tiết
                throw new Error(data.error || "Không thể xóa sản phẩm khỏi wishlist.");
            }
            // Thành công
            alert(data.message || "Xóa thành công");
            loadWishlist();
        })
        .catch(err => {
            alert("Lỗi khi xóa: " + err.message);
        });
}