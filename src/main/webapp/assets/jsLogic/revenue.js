document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("revenueForm");
    const resultDiv = document.getElementById("revenueResult");

    form.addEventListener("submit", function (e) {
        e.preventDefault();
        const period = document.getElementById("period").value;

        let url = "/api/revenue";
        if (period) {
            url += `?period=${encodeURIComponent(period)}`;
        }

        fetch(url, {
            method: "GET",
            credentials: "include"
        })
        .then(response => {
            if (!response.ok) throw new Error("Không thể lấy dữ liệu doanh thu");
            return response.json();
        })
        .then(data => {
            // Hiển thị dữ liệu doanh thu theo các trường của RevenueDTO
            let html = "";
            if (data.period === "today") {
                html += `<p><strong>Doanh thu hôm nay:</strong> ${data.todayRevenue ?? 0} ${data.currency ?? "VND"}</p>`;
            } else if (data.period === "week") {
                html += `<p><strong>Doanh thu tuần này:</strong> ${data.weekRevenue ?? 0} ${data.currency ?? "VND"}</p>`;
            } else if (data.period === "month") {
                html += `<p><strong>Doanh thu tháng này:</strong> ${data.monthRevenue ?? 0} ${data.currency ?? "VND"}</p>`;
            } else if (data.period === "year") {
                html += `<p><strong>Doanh thu năm nay:</strong> ${data.yearRevenue ?? 0} ${data.currency ?? "VND"}</p>`;
            } else {
                html += `<p><strong>Doanh thu hôm nay:</strong> ${data.todayRevenue ?? 0} ${data.currency ?? "VND"}</p>`;
                html += `<p><strong>Doanh thu tuần này:</strong> ${data.weekRevenue ?? 0} ${data.currency ?? "VND"}</p>`;
                html += `<p><strong>Doanh thu tháng này:</strong> ${data.monthRevenue ?? 0} ${data.currency ?? "VND"}</p>`;
                html += `<p><strong>Doanh thu năm nay:</strong> ${data.yearRevenue ?? 0} ${data.currency ?? "VND"}</p>`;
            }
            resultDiv.innerHTML = html;
        })
        .catch(error => {
            resultDiv.innerHTML = `<span style=\"color:red\">${error.message}</span>`;
        });
    });
});



