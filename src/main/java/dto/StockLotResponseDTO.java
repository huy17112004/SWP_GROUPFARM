package dto;

import lombok.*;
import java.time.LocalDate;
import java.util.Date;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class StockLotResponseDTO {
    private int id;
    private int productId;
    private String productName;
    private int warehouseId;
    private int quantity;
    private Date importDate;
    private Date expiredDate;
}