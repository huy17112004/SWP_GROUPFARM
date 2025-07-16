package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "StockTransfer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockTransfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WholesaleOrderID", nullable = false)
    private WholesaleOrder order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SourceWarehouseID", nullable = false)
    private Warehouse sourceWarehouse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DestinationWarehouseID", nullable = false)
    private Warehouse destinationWarehouse;

    @Column(name = "Status", length = 16)
    private String status; // PENDING, TRANSFERRING, COMPLETED

    @Column(name = "RequestedAt")
    private LocalDateTime requestedAt;

    @Column(name = "CompletedAt")
    private LocalDateTime completedAt;

    @OneToMany(mappedBy = "stockTransfer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<StockTransferItem> items;
}
