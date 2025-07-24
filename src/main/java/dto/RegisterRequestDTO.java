package dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class RegisterRequestDTO {
    private String username;
    private String email;
    private String password;
    private String confirmPassword;
    // base URL của verify endpoint, ví dụ "https://your-domain.com/verify"
    private String verificationBaseUrl;
}