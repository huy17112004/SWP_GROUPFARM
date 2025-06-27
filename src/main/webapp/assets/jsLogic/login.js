document.getElementById("loginForm").addEventListener("submit", function (event) {
    event.preventDefault();

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    console.log("Attempting login with username:", username);

    fetch("/ASSSWP_war/api/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password })
    })
    .then(async (response) => {
        console.log("Response status:", response.status);
        const data = await response.json();
        console.log("Response data:", data);

        if (response.ok) {
            // Xử lý chuyển hướng dựa vào loại tài khoản
            switch(data.accountType) {
                case "ADMIN":
                    window.location.href = "/back-end/admin/dashboard.html";
                    break;
                case "SALER":
                    window.location.href = "/back-end/saler/dashboard.html";
                    break;
                case "MANAGER":
                    window.location.href = "/back-end/manager/dashboard.html";
                    break;
                case "SHIPPER":
                    window.location.href = "/back-end/shipper/dashboard.html";
                    break;
                case "WAREHOUSE_MANAGER":
                    window.location.href = "/back-end/warehouse/dashboard.html";
                    break;
                case "WAREHOUSE_STAFF":
                    window.location.href = "/back-end/warehouse/staff-dashboard.html";
                    break;
                case "WHOLESALE_CUSTOMER":
                    window.location.href = "/ASSSWP_war/front-end/index.html";
                    break;
                default:
                    alert("Unknown account type");
            }
        } else {
            alert(data.message); // Hiển thị thông báo lỗi
        }
    })
    .catch(error => {
        console.error("Login error details:", error);
        console.error("Error name:", error.name);
        console.error("Error message:", error.message);
        console.error("Error stack:", error.stack);
        alert("Có lỗi xảy ra, vui lòng thử lại sau.");
    });
});