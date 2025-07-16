package entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "StockTransfer")
public class StockTransfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

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

    @OneToMany(mappedBy = "stockTransfer", fetch = FetchType.LAZY)
    private List<StockTransferItem> items;
}