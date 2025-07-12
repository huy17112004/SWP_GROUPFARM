package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "OrderItemAllocation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemAllocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OrderItemID", nullable = false)
    private WholesaleOrderItem orderItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "StockLotID", nullable = false)
    private StockLot stockLot;

    @Column(name = "Quantity")
    private int quantity;

    @Column(name = "Status", length = 16)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "StockTransferItemID")
    private StockTransferItem stockTransferItem;
}
