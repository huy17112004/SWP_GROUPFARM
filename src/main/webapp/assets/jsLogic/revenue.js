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
        console.log("Fetch URL:", url);

        fetch(url, {
            method: "GET",
            credentials: "include"
        })
            .then(response => {
                if (!response.ok) throw new Error("Không thể lấy dữ liệu doanh thu");
                return response.json();
            })
            .then(data => {
                const format = (value) => Number(value || 0).toLocaleString("vi-VN") + " VND";
                let text = "0 VND";

                if (data.period === "today") text = format(data.todayRevenue);
                else if (data.period === "week") text = format(data.weekRevenue);
                else if (data.period === "month") text = format(data.monthRevenue);
                else if (data.period === "year") text = format(data.yearRevenue);
                else {
                    const total = (data.todayRevenue || 0) + (data.weekRevenue || 0) + (data.monthRevenue || 0) + (data.yearRevenue || 0);
                    text = format(total);
                }

                revenueText.textContent = text;
                resultDiv.innerHTML = `<small>(Kỳ: ${data.period ?? "Tất cả"})</small>`;

                // Kiểm tra có labels + values hay không
                if (Array.isArray(data.labels) && Array.isArray(data.values)) {
                    const labels = data.labels;
                    const values = data.values.map(val => Number(val)); // ép kiểu
                    renderRevenueChart(labels, values);
                } else {
                    console.warn("Không có dữ liệu biểu đồ");
                }
            })
            .catch(error => {
                resultDiv.innerHTML = `<span style="color:red">${error.message}</span>`;
                console.error(error);
            });
    });
    // function renderRevenueChart(labels, values) {
    //     const chartEl = document.querySelector("#revenueChart");
    //     if (!chartEl) {
    //         console.error("Không tìm thấy phần tử #revenueChart");
    //         return;
    //     }
    //
    //     // Xóa biểu đồ cũ (nếu có)
    //     if (window.revenueApexChart) {
    //         window.revenueApexChart.destroy();
    //     }
    //
    //     // Tạo biểu đồ mới
    //     window.revenueApexChart = new ApexCharts(chartEl, {
    //         chart: { type: 'bar', height: 250 },
    //         series: [{ name: "Doanh thu", data: values }],
    //         xaxis: { categories: labels },
    //         yaxis: {
    //             labels: {
    //                 formatter: val => val.toLocaleString('vi-VN') + ' đ'
    //             }
    //         },
    //         dataLabels: { enabled: false },
    //         colors: ['#34c38f'],
    //         title: { text: 'Biểu đồ doanh thu', align: 'center' }
    //     });
    //
    //     window.revenueApexChart.render();
    // }
});
