document.getElementById("forgotForm").addEventListener("submit", function () {
    event.preventDefault();

    const email = document.getElementById("email").value;

    fetch("/ASSSWP_war/api/forgot-password", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email: email })
    })
        .then(res => {
            console.log("Raw response:", res);
            return res.json();
        })
        .then(data => {
            console.log("Parsed JSON:", data);
            if (data.success) {
                alert(data.message);
                window.location.href = "/ASSSWP_war/front-end/otp.html";
            } else {
                alert("Gửi OTP thất bại: " + data.message);
            }
        })
        .catch(err => {
            console.error("Lỗi:", err);
            alert("Lỗi hệ thống khi gửi OTP");
        });

});
