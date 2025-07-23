package dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class VerifyOtpRequestDTO {
    private String email;
    private String otp;
}