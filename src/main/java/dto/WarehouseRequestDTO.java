package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseRequestDTO {
    private String warehouseName;
    private String warehousePhone;
    private String street;
    private Float latitude;
    private Float longitude;
    private int wardId;

    // Getters & setters
}
