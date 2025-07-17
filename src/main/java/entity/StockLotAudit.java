package entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * Nhật ký thay đổi số lượng lô.
 */
@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Table(name = "StockLotAudit")
public class StockLotAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AuditID")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "StockLotID", nullable = false)
    private StockLot stockLot;

    @Column(name = "BeforeQty", nullable = false)
    private int beforeQty;

    @Column(name = "ChangeQty", nullable = false)
    private int changeQty;

    @Column(name = "AfterQty", nullable = false)
    private int afterQty;

    @Column(name = "ActionType", length = 20, nullable = false)
    private String actionType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WarehouseStaffID", nullable = false)
    private WarehouseStaff performedBy;

    @Column(name = "ActionTime", nullable = false)
    private LocalDateTime actionTime;
}