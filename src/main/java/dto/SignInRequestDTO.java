package dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class SignInRequestDTO {
    private String username;
    private String password;
    private boolean rememberMe;
}
