package dto;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemCustomerDTO {
    private Integer orderItemId;           // orderItemId
    private String productName;
    private BigDecimal unitPrice;
    private Integer quantity;
    private BigDecimal subTotal;
}