package dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {
    private Long userId;
    private Long productId;
    private String productName;
    private String productImage;
    private BigDecimal wholesalePrice;
    private Integer quantity;

}


