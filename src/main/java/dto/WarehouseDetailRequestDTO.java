package dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class WarehouseDetailRequestDTO {
    private String warehouseName;
    private String warehousePhone;
    private String street;
    private int wardId;
    private Integer managerId;
    private Integer staffId;
}
