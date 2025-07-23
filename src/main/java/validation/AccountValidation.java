package validation;

import org.hibernate.Session;
import org.hibernate.query.Query;
import util.JpaUtil;

public class AccountValidation {

    // Chỉ kiểm tra cú pháp chung: username, email, password, confirmPassword
    public static void validateSyntax(String username,
                                      String email,
                                      String password,
                                      String confirmPassword) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username không được để trống");
        }
        if (email == null || !email.matches("^[\\w.%+-]+@[\\w.-]+\\.[A-Za-z]{2,6}$")) {
            throw new IllegalArgumentException("Email không hợp lệ");
        }
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("Password phải có ít nhất 8 ký tự");
        }
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Xác nhận mật khẩu không khớp");
        }
        // …các rule khác về ký tự, độ mạnh mật khẩu…
    }

    // Nếu cần vẫn giữ validateAccountCreation, nhưng chỉ gọi validateSyntax bên trong
    public static void validateAccountCreation(String username,
                                               String email,
                                               String password,
                                               String confirmPassword,
                                               Class<?> userClass) {
        validateSyntax(username, email, password, confirmPassword);
        // BỎ phần kiểm tra unique email/username ở đây!
    }
}