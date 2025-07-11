console.log("Revenue.js loaded");
document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("revenueForm");
    const resultDiv = document.getElementById("revenueResult");
    const revenueText = document.getElementById("totalRevenue");

    form.addEventListener("submit", function (e) {
        e.preventDefault();
        const period = document.getElementById("period").value;

        let url = "/api/revenue";
        if (period) {
            url += `?period=${encodeURIComponent(period)}`;
        }
        console.log("Fetch URL:", url); // 👈 THÊM DÒNG NÀY


        fetch(url, {
            method: "GET",
            credentials: "include"
        })
            .then(response => {
                if (!response.ok) throw new Error("Không thể lấy dữ liệu doanh thu");
                return response.json();
            })
            .then(data => {
                let text = "0 VND";
                if (data.period === "today" && data.todayRevenue) {
                    text = `${data.todayRevenue} VND`;
                } else if (data.period === "week" && data.weekRevenue) {
                    text = `${data.weekRevenue} VND`;
                } else if (data.period === "month" && data.monthRevenue) {
                    text = `${data.monthRevenue} VND`;
                } else if (data.period === "year" && data.yearRevenue) {
                    text = `${data.yearRevenue} VND`;
                } else {
                    const total = (data.todayRevenue || 0) +
                        (data.weekRevenue || 0) +
                        (data.monthRevenue || 0) +
                        (data.yearRevenue || 0);
                    text = `${total} VND`;
                }

                revenueText.textContent = text;
                resultDiv.innerHTML = `<small>(Kỳ: ${data.period ?? "Tất cả"})</small>`;
            })
            .catch(error => {
                resultDiv.innerHTML = `<span style="color:red">${error.message}</span>`;
            });
    });
});
