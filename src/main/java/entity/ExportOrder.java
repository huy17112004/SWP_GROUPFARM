// entity/ExportOrder.java
package entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Phiếu xuất hàng từ kho.
 */
@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Table(name = "ExportOrder")
public class ExportOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ExportOrderID")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WarehouseID", nullable = false)
    private Warehouse warehouse;

    @Column(name = "Destination", length = 100, nullable = false)
    private String destination;

    @Column(name = "OrderDate", nullable = false)
    private LocalDateTime orderDate;

    // Người nhân viên xuất hàng
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WarehouseStaffID", nullable = false)
    private WarehouseStaff createdBy;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "exportOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExportOrderItem> items;
}