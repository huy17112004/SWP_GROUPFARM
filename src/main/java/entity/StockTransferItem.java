package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "StockTransferItem")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OrderItemAllocationID")
    private OrderItemAllocation orderItemAllocation;

}

