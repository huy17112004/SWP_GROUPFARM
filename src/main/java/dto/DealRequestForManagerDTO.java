package dto;

import entity.DealRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DealRequestForManagerDTO {
    int dealId;
    String productName;
    int quantity;
    BigDecimal originalPrice;
    BigDecimal proposedPrice;
    LocalDateTime requestedAt;
    String message;
    String status;
    CustomerInformation customerInformation;
    OrderInformation orderInformation;

    public DealRequestForManagerDTO(DealRequest dealRequest) {
        this.dealId = dealRequest.getId();
        this.productName = dealRequest.getOrderItem().getProduct().getProductName();
        this.quantity = dealRequest.getOrderItem().getQuantity();
        this.originalPrice = dealRequest.getOrderItem().getPrice();
        this.proposedPrice = dealRequest.getProposedPrice();
        this.requestedAt = dealRequest.getRequestedAt();
        this.message = dealRequest.getMessage();
        this.status = dealRequest.getStatus();
        this.customerInformation = new CustomerInformation(dealRequest);
        this.orderInformation = new OrderInformation(dealRequest);
    }

    @Getter
    @Setter
    class CustomerInformation {
        String customerName;
        String phone;
        String email;
        String addressDelivery;

        public CustomerInformation(DealRequest dealRequest) {
            this.customerName = dealRequest.getOrderItem().getOrder().getCustomer().getCompanyName();
            this.phone = dealRequest.getOrderItem().getOrder().getCustomer().getPhone();
            this.email = dealRequest.getOrderItem().getOrder().getCustomer().getEmail();
            this.addressDelivery = dealRequest.getOrderItem().getOrder().getDeliveryAddress().toStringAddress();
        }

    }

    @Getter
    @Setter
    class OrderInformation {
        int orderId;
        BigDecimal totalPrice;
        BigDecimal shippingPrice;
        BigDecimal itemsTotal;
        Date createdAt;

        public OrderInformation(DealRequest dealRequest) {
            this.orderId = dealRequest.getOrderItem().getOrder().getId();
            this.totalPrice = dealRequest.getOrderItem().getOrder().getTotalPrice();
            this.shippingPrice = dealRequest.getOrderItem().getOrder().getEstimatedShipFee();
            this.itemsTotal = dealRequest.getOrderItem().getOrder().getItemsTotal();
            this.createdAt = dealRequest.getOrderItem().getOrder().getCreatedAt();
        }

    }
}
