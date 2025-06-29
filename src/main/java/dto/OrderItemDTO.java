package dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter @NoArgsConstructor @AllArgsConstructor
public class OrderItemDTO {
    private Integer id;
    private Integer orderId;
    private Integer productId;
    private String productName;
    private BigDecimal unitPrice;
    private Integer quantity;
}
