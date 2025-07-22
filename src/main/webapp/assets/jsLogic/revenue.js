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
    let revenueChartInstance = null;

    function renderRevenueChart(labels, values) {
        const ctx = document.getElementById("revenueChart").getContext("2d");

        // Xóa biểu đồ cũ nếu có
        if (revenueChartInstance) {
            revenueChartInstance.destroy();
        }

        revenueChartInstance = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Doanh thu (VND)',
                    data: values,
                    backgroundColor: '#34c38f'
                }]
            },
            options: {
                responsive: true,
                scales: {
                    y: {
                        beginAtZero: true,
                        ticks: {
                            callback: val => val.toLocaleString('vi-VN') + ' đ'
                        }
                    }
                },
                plugins: {
                    title: {
                        display: true,
                        text: 'Biểu đồ doanh thu',
                        align: 'center',
                        font: {size: 18}
                    },
                    legend: {
                        display: false
                    }
                }
            }
        });
    }
});


