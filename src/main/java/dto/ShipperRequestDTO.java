// src/main/java/dto/ShipperRequestDTO.java
package dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ShipperRequestDTO {
    private String username;
    private String rawPassword;
    private String name;
}
