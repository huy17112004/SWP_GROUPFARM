package dto;

import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderCustomerDTO {
    private Integer orderId;
    private List<OrderItemCustomerDTO> items;
    private BigDecimal totalAmount;
    private BigDecimal shippingFee;
    private String status;
}