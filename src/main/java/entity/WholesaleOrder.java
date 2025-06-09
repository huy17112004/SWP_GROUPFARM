package entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
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
    private int id;

    @Column(name = "Status", length = 38)
    private String status;

    @Column(name = "EstimatedShipFee", precision = 18, scale = 2)
    private BigDecimal estimatedShipFee;

    @Column(name = "TotalPrice", precision = 18, scale = 2)
    private BigDecimal totalPrice;

    @Column(name = "TotalPriceDeal", precision = 18, scale = 2)
    private BigDecimal totalPriceDeal;

    @Column(name = "CreatedAt")
    private Date createdAt;

    @Column(name = "ConfirmedAt")
    private Date confirmedAt;

    @Column(name = "DealCompletedAt")
    private Date dealCompletedAt;

    @Column(name = "ContractSignedAt")
    private Date contractSignedAt;

    @Column(name = "DepositAmount", precision = 18, scale = 2)
    private BigDecimal depositAmount;

    /* n WholesaleOrder ↔ 1 Contract */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ContractID", nullable = false, insertable = false, updatable = false)
    private Contract contract;

    /* 1 WholesaleOrder ↔ n WholesaleOrderItem */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<WholesaleOrderItem> items;

    /* 1 WholesaleOrder ↔ n ShippingLog */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<ShippingLog> shippingLogs;

    /* 1 WholesaleOrder ↔ n OrderRisk */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<OrderRisk> risks;
}