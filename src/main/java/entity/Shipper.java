package entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Shipper")
public class Shipper extends Account {

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "WarehouseID")
    private Integer warehouseId;

    @Column(name = "Email", nullable = false, length = 100)
    private String email;

    @Column(name = "Phone", nullable = false, length = 20)
    private String phone;

    @Column(name = "CreateAt", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "Status", nullable = false, length = 50)
    private String status;

    @OneToOne(mappedBy = "shipper")
    private ShippingLog shippingLog;

    // Getters & Setters...
}
