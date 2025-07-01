// entity/ExportOrderItem.java
package entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Chi tiết dòng sản phẩm trong phiếu xuất.
 */
@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Table(name = "ExportOrderItem")
public class ExportOrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ExportOrderItemID")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ExportOrderID", nullable = false)
    private ExportOrder exportOrder;

    // Liên kết trực tiếp đến StockLot thay vì Product để kiểm soát từng lô
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "StockLotID", nullable = false)
    private StockLot stockLot;

    // Số lượng (ví dụ: kg, gói, thùng ...)
    @Column(name = "Quantity", nullable = false)
    private int quantity;
}