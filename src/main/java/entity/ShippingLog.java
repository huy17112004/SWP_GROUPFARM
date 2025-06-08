package entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ShippingLog")
public class ShippingLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ShippingLogID")
    private int shippingLogID;

    @Column(name = "OrderID", nullable = false)
    private int orderID;

    @Column(name = "CustomerID", nullable = false)
    private int customerID;

    @Column(name = "ShipperID", nullable = false)
    private int shipperID;

    @Column(name = "Status", length = 50, nullable = false)
    private String status;

    @Column(name = "ShippedAtDateTime")
    private LocalDateTime shippedAtDateTime;

    /* n ShippingLog ↔ 1 WholesaleOrder */
    @ManyToOne
    @JoinColumn(name = "OrderID", nullable = false, insertable = false, updatable = false)
    private WholesaleOrder order;
}