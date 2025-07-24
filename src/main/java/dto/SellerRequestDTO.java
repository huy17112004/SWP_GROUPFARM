// src/main/java/dto/SellerRequestDTO.java
package dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class SellerRequestDTO {
    private String username;
    private String rawPassword;
    private String name;
    private String email;
    private String phone;
}
