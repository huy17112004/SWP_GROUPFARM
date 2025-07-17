package entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Chi tiết dòng lô xuất trong phiếu.
 */
@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Table(name = "ExportOrderItem")
public class ExportOrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ExportOrderItemID")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ExportOrderID", nullable = false)
    private ExportOrder exportOrder;

    // Liên kết đến lô kho
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "StockLotID", nullable = false)
    private StockLot stockLot;

    // Số lượng (ví dụ: kg)
    @Column(name = "Quantity", nullable = false)
    private int quantity;
}
