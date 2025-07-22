
    document.getElementById("logoutBtn").addEventListener("click", function () {
    fetch("/api/auth/logout", {
        method: "POST"
    })
        .then(response => {
            if (response.ok) {
                // Logout thành công: chuyển về trang login
                window.location.href = "/login.html"; // hoặc login.jsp nếu bạn dùng JSP
            } else {
                alert("Đăng xuất thất bại!");
            }
        })
        .catch(error => {
            console.error("Lỗi logout:", error);
            alert("Đã xảy ra lỗi khi đăng xuất.");
        });
});