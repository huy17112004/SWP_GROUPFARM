package dto;

import lombok.*;
import java.time.LocalDate;
import java.util.Date;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class StockLotRequestDTO {
    private int productId;
    private int warehouseId;
    private int quantity;
    private Date importDate;
    private Date expiredDate;
}