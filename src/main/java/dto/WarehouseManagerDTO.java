// src/main/java/dto/WarehouseManagerDTO.java
package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO dùng để truyền thông tin WarehouseManager lên client
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseManagerDTO {
    private int managerId;
    private String name;
    private String email;
    private String phone;
}
