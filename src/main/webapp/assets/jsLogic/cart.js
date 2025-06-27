// cart.js
function addToCart() {
    const productName = document.getElementById('product-name').textContent;
    const quantity = parseInt(document.getElementById('quantity-input').value);

    if (quantity <= 0) {
        alert('Please select a valid quantity.');
        return;
    }

    // Gọi API để lấy productId từ productName
    fetch('/your-project-name/api/products?name=' + encodeURIComponent(productName))
        .then(response => {
            if (!response.ok) throw new Error('Product not found');
            return response.json();
        })
        .then(data => {
            const productId = data.id;
            const cartItem = {
                productId: productId,
                userId: window.userId, // Lấy từ session
                quantity: quantity
            };

            // Gửi dữ liệu đến /api/cart
            return fetch('/your-project-name/api/cart', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(cartItem)
            });
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Có lỗi khi thêm vào giỏ hàng');
            }
            return response.json();
        })
        .then(data => {
            alert('Đã thêm vào giỏ hàng!');
            console.log(data);
            window.location.href = 'cart.html'; // Chuyển hướng sau khi thêm thành công
        })
        .catch(error => {
            alert('Lỗi: ' + error.message);
        });
}

// Chọn trọng lượng (chỉ để hiển thị)
document.querySelectorAll('.weight-option').forEach(option => {
    option.addEventListener('click', function() {
        document.querySelectorAll('.weight-option').forEach(opt => opt.classList.remove('active'));
        this.classList.add('active');
    });
});

// Tăng/giảm số lượng
document.querySelector('.qty-right-plus').addEventListener('click', function() {
    let quantity = parseInt(document.getElementById('quantity-input').value);
    document.getElementById('quantity-input').value = quantity + 1;
});

document.querySelector('.qty-left-minus').addEventListener('click', function() {
    let quantity = parseInt(document.getElementById('quantity-input').value);
    if (quantity > 0) document.getElementById('quantity-input').value = quantity - 1;
});

// Khởi tạo Feather Icons
feather.replace();