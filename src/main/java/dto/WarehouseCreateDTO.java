// src/main/java/dto/WarehouseCreateDTO.java
package dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Dùng để bind JSON từ client khi tạo kho mới
 * Bao gồm cả thông tin address (street, wardId, lat/lng).
 */
@Getter @Setter @NoArgsConstructor
public class WarehouseCreateDTO {
    // Warehouse fields
    private String warehouseName;
    private String warehousePhone;

    // Address fields
    private String street;
    private int wardId;
    private float latitude;
    private float longitude;
}
