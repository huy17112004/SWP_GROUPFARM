package dto;

import entity.DealRequest;
import entity.WholesaleOrderItem;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class DealRequestViewDTO {
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

    public DealRequestViewDTO(WholesaleOrderItem item, DealRequest dealRequest) {
        this.orderItemId = item.getId();
        this.productName = item.getProduct().getProductName();
        this.quantity = item.getQuantity();
        this.originalPrice = item.getPrice();
        if (dealRequest != null) {
            this.dealId = dealRequest.getId();
            this.proposedPrice = dealRequest.getProposedPrice();
            this.status = dealRequest.getStatus();
            this.requestedAt = dealRequest.getRequestedAt();
            this.respondedAt = dealRequest.getRespondedAt();
            this.message = dealRequest.getMessage();
        }

    }
}
