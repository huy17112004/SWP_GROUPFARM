package dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class OrderSellerDTO {
    private int orderId;
    private String orderCode;
    private Date createdAt;
    private String status;
    private BigDecimal totalItem;
    private BigDecimal totalPrice;
    private BigDecimal estimatedShipFee;
    private String customerName;
    private String customerPhone;
    private String customerEmail;
    private String deliveryAddress;
    private LocalDateTime deliveryDate;
    private List<OrderItemSellerDTO> items;

    public OrderSellerDTO() {
    }

    public OrderSellerDTO(int orderId, String orderCode, Date createdAt, String status, 
                         BigDecimal totalItem, BigDecimal totalPrice, BigDecimal estimatedShipFee,
                         String customerName, String customerPhone, String customerEmail,
                         String deliveryAddress, LocalDateTime deliveryDate, List<OrderItemSellerDTO> items) {
        this.orderId = orderId;
        this.orderCode = orderCode;
        this.createdAt = createdAt;
        this.status = status;
        this.totalItem = totalItem;
        this.totalPrice = totalPrice;
        this.estimatedShipFee = estimatedShipFee;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.customerEmail = customerEmail;
        this.deliveryAddress = deliveryAddress;
        this.deliveryDate = deliveryDate;
        this.items = items;
    }
} 