package dto;

import lombok.*;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class StockLotResponseDTO {
    private int id;
    private int productId;
    private String productName;
    private int warehouseId;
    private int quantity;
    private LocalDate importDate;
    private LocalDate expiredDate;
}