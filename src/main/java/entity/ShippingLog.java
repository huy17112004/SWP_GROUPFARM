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
    private int id;

    @Column(name = "Status", length = 50, nullable = false)
    private String status;

    @Column(name = "ShippedAtDateTime")
    private LocalDateTime shippedAtDateTime;

    @OneToOne
    @JoinColumn(name = "CustomerID", nullable = false, insertable = false, updatable = false)
    private WholesaleCustomer customer;

    @OneToOne
    @JoinColumn(name = "ShipperID", nullable = false, insertable = false, updatable = false)
    private Shipper shipper;

    /* n ShippingLog ↔ 1 WholesaleOrder */
    @ManyToOne
    @JoinColumn(name = "OrderID", nullable = false, insertable = false, updatable = false)
    private WholesaleOrder order;
}