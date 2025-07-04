package dto;

import lombok.*;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class StockLotRequestDTO {
    private int productId;
    private int warehouseId;
    private int quantity;
    private LocalDate importDate;
    private LocalDate expiredDate;
}