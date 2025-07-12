package entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


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
    @JoinColumn(name = "ProductID", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WarehouseID", nullable = false)
    private Warehouse warehouse;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "stockLot")
    private List<ExportOrderItem> exportOrderItems;

    @OneToMany(mappedBy = "stockLot", fetch = FetchType.LAZY)
    private List<OrderItemAllocation>  orderItemAllocations;

    @OneToMany(mappedBy = "stockLot", fetch = FetchType.LAZY)
    private List<StockTransferItem> stockTransferItems;
}
