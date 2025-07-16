package dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderSellerDTO {
    private int orderId;
    private String orderCode;
    private Date createdAt;
    private String status;
    private BigDecimal totalPrice;
    private BigDecimal estimatedShipFee;
    private String customerName;
    private String customerPhone;
    private String customerEmail;
    private String deliveryAddress;
    private List<OrderItemSellerDTO> items;

    public OrderSellerDTO() {
    }

    public OrderSellerDTO(int orderId, String orderCode, Date createdAt, String status, 
                         BigDecimal totalPrice, BigDecimal estimatedShipFee, 
                         String customerName, String customerPhone, String customerEmail,
                         String deliveryAddress, List<OrderItemSellerDTO> items) {
        this.orderId = orderId;
        this.orderCode = orderCode;
        this.createdAt = createdAt;
        this.status = status;
        this.totalPrice = totalPrice;
        this.estimatedShipFee = estimatedShipFee;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.customerEmail = customerEmail;
        this.deliveryAddress = deliveryAddress;
        this.items = items;
    }

    // Getters and Setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getEstimatedShipFee() {
        return estimatedShipFee;
    }

    public void setEstimatedShipFee(BigDecimal estimatedShipFee) {
        this.estimatedShipFee = estimatedShipFee;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public List<OrderItemSellerDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemSellerDTO> items) {
        this.items = items;
    }
} 