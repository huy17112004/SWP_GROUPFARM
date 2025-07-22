package dto;

import java.math.BigDecimal;

public class ShippingOrderDTO {
    private int orderId;
    private String productName;
    private String status;
    private BigDecimal totalPrice;

    public ShippingOrderDTO(int orderId, String productName, String status, BigDecimal totalPrice) {
        this.orderId = orderId;
        this.productName = productName;
        this.status = status;
        this.totalPrice = totalPrice;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getProductName() {
        return productName;
    }

    public String getStatus() {
        return status;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
}
