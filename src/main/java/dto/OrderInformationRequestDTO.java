package dto;

import entity.WholesaleOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderInformationRequestDTO {
    int orderId;
    Date createdAt;
    String status;
    BigDecimal itemTotal;
    BigDecimal shippingFee;
    BigDecimal totalPrice;
    String customerName;
    String customerEmail;
    String customerPhone;
    String addressDelivery;



    public OrderInformationRequestDTO(WholesaleOrder order) {
        this.orderId = order.getId();
        this.createdAt = order.getCreatedAt();
        this.status = order.getStatus();
        this.itemTotal = order.getItemsTotal();
        this.shippingFee = order.getEstimatedShipFee();
        this.totalPrice = order.getTotalPrice();
        this.customerName = order.getCustomer().getCompanyName();
        this.customerEmail = order.getCustomer().getEmail();
        this.customerPhone = order.getCustomer().getPhone();
        this.addressDelivery = order.getDeliveryAddress().toStringAddress();
    }
}
