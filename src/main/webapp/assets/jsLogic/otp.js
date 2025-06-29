document.addEventListener("DOMContentLoaded", function () {
    const inputs = document.querySelectorAll("#otp input");

    inputs.forEach((input, index) => {
        input.addEventListener("input", (e) => {
            const value = e.target.value;
            if (value.length === 1 && index < inputs.length - 1) {
                inputs[index + 1].focus();
            }
        });

        input.addEventListener("keydown", (e) => {
            if (e.key === "Backspace" && !e.target.value && index > 0) {
                inputs[index - 1].focus();
            }
        });

        document.getElementById("otp").addEventListener("paste", function (e) {
            e.preventDefault();
            const pasteData = e.clipboardData.getData("text").trim();
            if (pasteData.length === inputs.length && /^\d+$/.test(pasteData)) {
                inputs.forEach((inp, i) => inp.value = pasteData[i]);
                inputs[inputs.length - 1].focus();
            }
        });
    });

    document.getElementById("otpForm").addEventListener("submit", function (event) {
        event.preventDefault();

        const email = document.getElementById("email").value;
        const newPassword = document.getElementById("newPassword").value;

        const otp = Array.from(inputs).map(input => input.value).join('');

        fetch("/ASSSWP_war/api/reset-password", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({
                email: email,
                otp: otp,
                newPassword: newPassword
            })
        })
            .then(res => res.json())
            .then(data => {
                if (data.success) {
                    alert(data.message);
                    window.location.href = "/ASSSWP_war/front-end/login.html";
                } else {
                    alert("Đặt lại mật khẩu thất bại: " + data.message);
                }
            })
            .catch(err => {
                console.error("Lỗi:", err);
                alert("Lỗi hệ thống khi đặt lại mật khẩu");
            });
    });

    document.querySelector(".send-box a").addEventListener("click", function () {
        const email = document.getElementById("email").value;

        if (!email) {
            alert("Vui lòng nhập email trước khi yêu cầu gửi lại OTP.");
            return;
        }

        fetch("/ASSSWP_war/api/forgot-password", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({email: email})
        })
            .then(res => res.json())
            .then(data => {
                if (data.success) {
                    alert("OTP mới đã được gửi đến email của bạn.");
                } else {
                    alert("Không thể gửi lại OTP: " + data.message);
                }
            })
            .catch(err => {
                console.error("Lỗi:", err);
                alert("Lỗi hệ thống khi gửi lại OTP.");
            });
    });
});
