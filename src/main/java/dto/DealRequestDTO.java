package dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class DealRequestDTO {
    private Integer id;
    private OrderItemDTO orderItem;

    private BigDecimal proposedPrice;

    // các trường tính toán sẵn
    private BigDecimal totalOriginalPrice;
    private BigDecimal totalProposedPrice;
    private BigDecimal discountAmount;
    private BigDecimal discountRate;

    private String status;
    private LocalDateTime requestedAt;
    private LocalDateTime respondedAt;
    private String message;

    // nếu muốn show tên khách luôn
    private String customerName;
}
