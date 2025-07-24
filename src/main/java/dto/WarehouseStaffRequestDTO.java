package dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class WarehouseStaffRequestDTO {
    private String username;
    private String rawPassword;
    private String name;
    private String email;
    private String phone;
    private int warehouseId;
}