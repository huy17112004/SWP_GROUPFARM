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
@Table(name = "StockLot")
public class StockLot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "StockLotID")
    private int id;

    @Column(name = "Quantity")
    private int quantity;

    @Column(name = "ImportDate")
    private Date importDate;

    @Column(name = "ExpiredDate")
    private Date expiredDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProductID", nullable = false, insertable = false, updatable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WarehouseID", nullable = false, insertable = false, updatable = false)
    private Warehouse warehouse;
}
