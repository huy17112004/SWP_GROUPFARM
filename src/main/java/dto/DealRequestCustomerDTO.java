package dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class DealRequestCustomerDTO {
    private Integer dealId;
    private Integer orderItemId;
    private String productName;
    private Integer quantity;
    private BigDecimal originalPrice;
    private BigDecimal proposedPrice;
    private String status;
    private LocalDateTime requestedAt;
    private LocalDateTime respondedAt;
    private String message;   // lý do reject hoặc ghi chú
}
