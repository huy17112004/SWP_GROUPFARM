package entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

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

    @Column(name = "Status", nullable = false, length = 50)
    private String status;

    @Column(name = "Timestamp", nullable = false)
    private LocalDateTime timestamp;

    @Column(name = "ShippedAtDateTime")
    private Date shippedAtDateTime;

    @Column(name = "Note", length = 255, nullable = true)
    private String note;

    /* n ShippingLog ↔ 1 WholesaleOrder */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OrderID", nullable = false)
    private WholesaleOrder order;

    /* n ShippingLog ↔ 1 Shipper (Account) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ShipperID", nullable = false)
    private Shipper shipper;
}