package entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "WholesaleOrder")
public class WholesaleOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WholesaleOrderID")
    private int wholesaleOrderID;

    @Column(name = "SalerID")
    private int salerID;

    @Column(name = "DeliveryAddressID")
    private int deliveryAddressID;

    @Column(name = "Status", length = 38)
    private String status;

    @Column(name = "EstimatedShipFee", precision = 18, scale = 2)
    private BigDecimal estimatedShipFee;

    @Column(name = "TotalPrice", precision = 18, scale = 2)
    private BigDecimal totalPrice;

    @Column(name = "TotalPriceDeal", precision = 18, scale = 2)
    private BigDecimal totalPriceDeal;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt;

    @Column(name = "ConfirmedAt")
    private LocalDateTime confirmedAt;

    @Column(name = "DealCompletedAt")
    private LocalDateTime dealCompletedAt;

    @Column(name = "ContractSignedAt")
    private LocalDateTime contractSignedAt;

    @Column(name = "DepositAmount", precision = 18, scale = 2)
    private BigDecimal depositAmount;

    @Column(name = "ContractID", nullable = false)
    private int contractID;

    /* n WholesaleOrder ↔ 1 Contract */
    @ManyToOne
    @JoinColumn(name = "ContractID", nullable = false, insertable = false, updatable = false)
    private Contract contract;

    /* 1 WholesaleOrder ↔ n WholesaleOrderItem */
    @OneToMany(mappedBy = "orderID", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WholesaleOrderItem> items;

    /* 1 WholesaleOrder ↔ n ShippingLog */
    @OneToMany(mappedBy = "orderID", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShippingLog> shippingLogs;

    /* 1 WholesaleOrder ↔ n OrderRisk */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderRisk> risks;
}