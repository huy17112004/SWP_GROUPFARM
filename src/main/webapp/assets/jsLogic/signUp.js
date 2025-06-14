document.getElementById("signUpForm").addEventListener("submit", function (event) {
    event.preventDefault();


    const email = document.getElementById("email").value;
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    fetch("/api/signup/customer", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password , email })
    })
        .then(async (response) => {
            const data = await response.json();

            if (response.ok) {
                alert(data.message); // Đăng ký thành công
                window.location.href = "/front-end/index.html";
            } else {
                alert(data.message); // Sai tài khoản, mật khẩu
            }
        })
        .catch(error => {
            console.error("Signup error:", error);
            alert("Có lỗi xảy ra, vui lòng thử lại sau.");
        });
});
