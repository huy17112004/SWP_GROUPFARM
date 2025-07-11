// ===== WISHLIST API UTILITIES =====

//  Lấy danh sách wishlist của user hiện tại
async function fetchWishlist() {
    const res = await fetch("/api/wishlist");
    if (!res.ok) throw new Error("Không thể lấy danh sách wishlist");
    return await res.json();
}

//  Kiểm tra sản phẩm có trong wishlist không
async function isInWishlist(productId) {
    const res = await fetch(`/api/wishlist/check/${productId}`);
    if (!res.ok) throw new Error("Lỗi khi kiểm tra wishlist");
    const json = await res.json();
    return json.inWishlist === true;
}

//  Thêm sản phẩm vào wishlist
async function addToWishlist(productId) {
    const formData = new URLSearchParams();
    formData.append("productId", productId);

    const res = await fetch("/api/wishlist", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: formData.toString()
    });

    if (!res.ok) {
        const err = await res.json();
        throw new Error(err.error || "Thêm sản phẩm vào wishlist thất bại");
    }

    const json = await res.json();
    return json.message;
}

//  Xoá sản phẩm khỏi wishlist
async function removeFromWishlist(productId) {
    const res = await fetch(`/api/wishlist/${productId}`, {
        method: "DELETE"
    });

    if (!res.ok) {
        const err = await res.json();
        throw new Error(err.error || "Xoá sản phẩm khỏi wishlist thất bại");
    }

    const json = await res.json();
    return json.message;
}

//  Toggle thêm / xoá wishlist (sử dụng trong button yêu thích)
async function toggleWishlist(productId, onToggleCallback) {
    try {
        const inList = await isInWishlist(productId);
        if (inList) {
            await removeFromWishlist(productId);
            alert("🗑 Đã xoá khỏi wishlist");
        } else {
            await addToWishlist(productId);
            alert("❤️ Đã thêm vào wishlist");
        }
        if (onToggleCallback) onToggleCallback(); // callback để render lại giao diện nếu cần
    } catch (e) {
        alert("Lỗi xử lý wishlist: " + e.message);
    }
}

//  Hiển thị danh sách wishlist ra HTML
async function renderWishlist(containerId = "wishlist-container") {
    try {
        const wishlist = await fetchWishlist();
        const container = document.getElementById(containerId);
        container.innerHTML = "";

        if (wishlist.length === 0) {
            container.innerHTML = "<p>Danh sách yêu thích trống.</p>";
            return;
        }

        wishlist.forEach(item => {
            const html = `
                <div class="wishlist-item">
                    <img src="${item.productImage}" alt="${item.productName}" width="100">
                    <h4>${item.productName}</h4>
                    <p>${item.productPrice.toLocaleString("vi-VN")} đ</p>
                    <small>Thêm lúc: ${new Date(item.createdAt).toLocaleString("vi-VN")}</small><br>
                    <button onclick="removeFromWishlist(${item.productId}).then(() => renderWishlist('${containerId}'))">
                        🗑 Xoá
                    </button>
                </div>
                <hr>
            `;
            container.insertAdjacentHTML("beforeend", html);
        });
    } catch (e) {
        console.error("Lỗi hiển thị wishlist:", e);
        document.getElementById(containerId).innerHTML = `<p class="text-danger">${e.message}</p>`;
    }
}
