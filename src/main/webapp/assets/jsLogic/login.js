document.getElementById("loginForm").addEventListener("submit", function (event) {
    event.preventDefault();

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    fetch("/ASSSWP_war/api/login/customer", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password })
    })
        .then(async (response) => {
            const data = await response.json();

            if (response.ok) {
                alert(data.message); // Đăng nhập thành công
                window.location.href = "/ASSSWP_war/front-end/index.html";
            } else {
                alert(data.message); // Sai tài khoản, mật khẩu
            }
        })
        .catch(error => {
            console.error("Login error:", error);
            alert("Có lỗi xảy ra, vui lòng thử lại sau.");
        });
});
