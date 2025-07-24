// src/main/java/dto/ShipperDTO.java
package dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ShipperDTO {
    private int id;
    private String username;
    private String name;
    private String status;
}
