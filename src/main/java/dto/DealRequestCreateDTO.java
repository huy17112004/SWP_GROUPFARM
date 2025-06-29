// 1. DTO nhận khi tạo deal (inbound)
package dto;

import lombok.*;

import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class DealRequestCreateDTO {
    private Integer orderItemId;       // từ form
    private BigDecimal proposedPrice;  // từ form
    private String message;
}