package dto;

import lombok.*;

import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class DealRequestFilterDTO {
    private java.util.List<String> statuses;       // e.g. ["PENDING","APPROVED"]
    private String customerName;                   // partial match
    private Integer productId;
    private String productName;                    // partial match

    private BigDecimal minTotalOriginalPrice;
    private BigDecimal maxTotalOriginalPrice;

    private BigDecimal minTotalProposedPrice;
    private BigDecimal maxTotalProposedPrice;

    private BigDecimal minDiscountRate;            // 0.1 = 10%
    private BigDecimal maxDiscountRate;

    private Integer minQuantity;
    private Integer maxQuantity;

    private String sortField;                      // "requestedAt", "discountRate", ...
    private boolean  sortAsc;                      // true = ASC, false = DESC
}
