package dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class WarehouseDetailRequestDTO {
    private String warehouseName;
    private String warehousePhone;
    // Địa chỉ:
    private String street;
    private int wardId;         // tham chiếu tới Ward đã có trong DB
    // Nhân sự:
    private Integer managerId;  // có thể null
    private Integer staffId;    // có thể null
}
