package dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class WarehouseDetailViewDTO {
    private int warehouseId;
    private String warehouseName;
    private String warehousePhone;
    private String fullAddress;
    private String managerName;
    private String staffName;
}

