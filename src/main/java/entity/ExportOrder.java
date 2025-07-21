// entity/ExportOrder.java
package entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;
import org.hibernate.annotations.Check;

/**
 * Phiếu xuất hàng: có thể là xuất đến khách (CUSTOMER) hoặc chuyển kho (TRANSFER).
 */
@Entity
@Check(constraints = "(OrderType = 'CUSTOMER' AND DestinationAddressID IS NOT NULL AND CustomerID IS NOT NULL AND ToWarehouseID IS NULL)"
        + " OR (OrderType = 'TRANSFER' AND ToWarehouseID IS NOT NULL AND DestinationAddressID IS NULL AND CustomerID IS NULL)")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Table(name = "ExportOrder")
public class ExportOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ExportOrderID")
    private int id;

    // Kho xuất hàng
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WarehouseID", nullable = false)
    private Warehouse warehouse;

    // Kiểu đơn: CUSTOMER = xuất đến khách, TRANSFER = chuyển kho
    @Enumerated(EnumType.STRING)
    @Column(name = "OrderType", length = 20, nullable = false)
    private OrderType orderType;

    // Nếu chuyển kho
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ToWarehouseID")
    private Warehouse toWarehouse;

    // Nếu xuất đến khách: địa chỉ nhận
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DestinationAddressID")
    private Address destinationAddress;

    // Ngày tạo phiếu
    @Column(name = "OrderDate", nullable = false)
    private LocalDateTime orderDate;

    // Nhân viên tạo
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WarehouseStaffID", nullable = false)
    private WarehouseStaff createdBy;

    // Người giao hàng (shipper)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ShipperID")
    private Shipper shipper;

    // Ngày dự kiến giao
    @Column(name = "ShippingDate")
    private LocalDate shippingDate;

    // Trạng thái vận chuyển (PENDING, SHIPPING, DELIVERED...)
    @Column(name = "ShippingStatus", length = 20)
    private String shippingStatus;

    // Ghi chú cho chuyến xuất này
    @Column(name = "Note", columnDefinition = "NVARCHAR(255)")
    private String note;

    @OneToMany(mappedBy = "exportOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExportOrderItem> items;
}