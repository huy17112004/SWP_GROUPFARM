
function getProductIdFromURL() {
    const params = new URLSearchParams(window.location.search);
    return params.get("productId");
}

function addToCart() {
    const productId = getProductIdFromURL();
    const quantityInput = document.getElementById('quantity-input');
    const quantity = parseInt(quantityInput?.value || "0");

    if (!productId || quantity <= 0) {
        alert('Vui lòng chọn số lượng hợp lệ và đảm bảo có productId.');
        return;
    }

    const cartItem = {
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
            alert(' Lỗi: ' + error.message);
        });
}


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
