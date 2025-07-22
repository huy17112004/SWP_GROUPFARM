document.addEventListener("DOMContentLoaded", function () {
    loadTopProducts();

    // Cho phép đổi thời gian bằng dropdown
    const periodSelector = document.getElementById("periodSelector");
    periodSelector.addEventListener("change", loadTopProducts);
});

function loadTopProducts() {
    const period = document.getElementById("periodSelector").value;
    const limit = 10;

    fetch(`/api/top-products?period=${period}&limit=${limit}`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json"
        }
    })
        .then(async response => {
            const data = await response.json();
            if (response.ok) {
                renderTopProductTable(data);
            } else {
                console.error("Lỗi khi lấy dữ liệu:", data.message);
                alert("Không thể lấy top sản phẩm.");
            }
        })
        .catch(error => {
            console.error("Lỗi fetch:", error);
            alert("Lỗi kết nối đến server.");
        });
}



function renderTopProductTable(products) {
    const tbody = document.getElementById("top-product-body");
    tbody.innerHTML = "";

    if (products.length === 0) {
        tbody.innerHTML = `<tr><td colspan="4" class="text-center">Không có dữ liệu</td></tr>`;
        return;
}

    products.forEach(p => {
    const row = document.createElement("tr");
    row.innerHTML = `
            <td class="product-image">
                <img src="${p.imageUrl || '/assets/images/placeholder.png'}" class="img-fluid" alt="">
            </td>
            <td><h6>${p.productName}</h6></td>
            <td><h6>${p.totalRevenue.toLocaleString("vi-VN")} ₫</h6></td>
            <td><h6>${p.totalQuantitySold}</h6></td>
        `;
    tbody.appendChild(row);
});
}

