package entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "StockTransferItem")
public class StockTransferItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "StockTransferID", nullable = false)
    private StockTransfer stockTransfer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "StockLotID", nullable = false)
    private StockLot stockLot;

    @Column(name = "Quantity", nullable = false)
    private int quantity;

    @OneToMany(mappedBy = "stockTransferItem", fetch = FetchType.LAZY)
    private List<OrderItemAllocation> orderItemAllocations;

}

