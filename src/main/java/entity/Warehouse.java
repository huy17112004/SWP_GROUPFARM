package entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "Warehouse")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WarehouseID")
    private int id;

    @Column(name = "WarehouseName", columnDefinition = "NVARCHAR(50)", nullable = false, length = 50)
    private String warehouseName;

    @Column(name = "WarehousePhone", columnDefinition = "NVARCHAR(15)", nullable = false, length = 12)
    private String warehousePhone;

    @OneToOne(mappedBy = "warehouse", orphanRemoval = true,fetch = FetchType.LAZY)
    private WarehouseStaff warehouseStaff;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "AddressID", nullable = false, unique = true) // unique đảm bảo one-to-one
    private Address address;

    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<StockLot> stockLots;

    @OneToMany(mappedBy = "sourceWarehouse", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<WholesaleOrder> orders;

    @OneToMany(mappedBy = "sourceWarehouse", fetch = FetchType.LAZY)
    private List<StockTransfer> stockTransfersAsSource;

    @OneToMany(mappedBy = "destinationWarehouse", fetch = FetchType.LAZY)
    private List<StockTransfer> stockTransfersAsDestination;
}
