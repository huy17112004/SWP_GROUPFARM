package dto;

import lombok.*;
import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ProductListResponseDTO {
    private int id;
    private String productName;
    private int entryPrice;
    private int retailPrice;
    private BigDecimal wholesalePrice;
    private String description;
    private int categoryId;
    private String categoryName;
}