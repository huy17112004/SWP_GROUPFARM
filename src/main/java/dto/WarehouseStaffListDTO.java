// src/main/java/dto/WarehouseStaffListDTO.java
package dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class WarehouseStaffListDTO {
    private int id;
    private String username;
    private String name;
    private String email;
    private String phone;
    private String warehouseName;
    private int warehouseId;
}
