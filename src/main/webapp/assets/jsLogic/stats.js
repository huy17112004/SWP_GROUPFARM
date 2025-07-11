function loadTotalSoldProducts() {
    fetch('/api/stats?action=sold-quantity')
        .then(response => {
            if (!response.ok) throw new Error('Không lấy được số lượng đã bán');
            return response.json();
        })
        .then(data => {
            const target = document.getElementById('totalSold');
            if (target) {
                target.innerText = data.totalSold;
            }
        })
        .catch(error => {
            console.error('Lỗi khi tải số lượng đã bán:', error);
            const target = document.getElementById('totalSold');
            if (target) {
                target.innerText = "N/A";
            }
        });
}
    document.addEventListener("DOMContentLoaded", function () {
        loadTotalSoldProducts();
    });
