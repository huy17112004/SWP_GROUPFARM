package entity;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(name = "WarehouseName", nullable = false, length = 50)
    private String warehouseName;

    @Column(name = "WarehousePhone", nullable = false, length = 12)
    private String warehousePhone;


    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "WarehouseManagerID")
    private WarehouseManager warehouseManager;

    @OneToOne
    @JoinColumn(name = "AddressID", nullable = false, unique = true) // unique đảm bảo one-to-one
    private Address address;
}
