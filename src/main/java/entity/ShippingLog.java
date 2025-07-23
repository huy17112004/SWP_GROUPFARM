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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ShipperID")
    private Shipper shipper;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OrderID")
    private WholesaleOrder order;
}