package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ProductListRequestDTO {
    private String productName;
    private BigDecimal entryPrice;
    private BigDecimal retailPrice;
    private BigDecimal wholesalePrice;;  // int
    private String description;
    private int categoryId;
    private String imageUrl;
}
