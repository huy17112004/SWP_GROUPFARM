// src/main/java/dto/SellerDTO.java
package dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class SellerDTO {
    private int id;
    private String username;
    private String name;
    private String email;
    private String phone;
    private String status;
}
