// src/main/java/dto/RoleSignInRequestDTO.java
package dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class RoleSignInRequestDTO {
    private String username;
    private String password;
    private String role; // "ADMIN", "WAREHOUSE", hoặc "SHIPPER"
}
