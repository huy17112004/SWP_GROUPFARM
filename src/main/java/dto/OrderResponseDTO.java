package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {
    private boolean success;
    private String message;
    private Integer orderId;
    private String failedReason; // có thể null nếu thành công
    private List<ProductStockInfo> stockDetails; // dùng khi thiếu hàng

    @Data
    public static class ProductStockInfo {
        private int productId;
        private String productName;
        private int required;
        private int available;
    }
}
