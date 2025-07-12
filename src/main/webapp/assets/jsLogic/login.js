document.getElementById("loginForm").addEventListener("submit", function (event) {
    event.preventDefault();

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    fetch("/api/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password })
    })
    .then(async (response) => {
        const data = await response.json();
        if (response.ok) {
            // Xử lý chuyển hướng dựa vào loại tài khoản
            switch(data.accountType) {
                case "ADMIN":
                    window.location.href = "/back-end/index-7.html";
                    break;
                case "SALER":
                    window.location.href = "/back-end/order-list-seller.html";
                    break;
                case "MANAGER":
                    window.location.href = "/back-end/sidebar-system-admin.html"; //-done
                    break;
                case "SHIPPER":
                    window.location.href = "/back-end/sidebar-shipper.html"; //-done
                    break;
                case "WAREHOUSE_MANAGER":
                    window.location.href = "/back-end/sidebar-WarehouseManager.html"; 
                    break;
                case "WAREHOUSE_STAFF":
                    window.location.href = "/back-end/sidebar-WarehouseStaff.html"; //-done
                    break;
                case "WHOLESALE_CUSTOMER":
                    window.location.href = "/front-end/index-7.html"; //-done
                    break;
                default:
                    alert("Unknown account type");
            }
        } else {
            alert(data.message); // Hiển thị thông báo lỗi
        }
    })
    .catch(error => {
        alert("Có lỗi xảy ra, vui lòng thử lại sau.");
    });
});