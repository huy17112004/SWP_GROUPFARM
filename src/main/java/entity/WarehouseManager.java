package entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "WarehouseManager")
public class WarehouseManager extends Account {

    @OneToMany(mappedBy = "warehouseManager",fetch = FetchType.LAZY)
    private List<Warehouse> warehouses;

    @Column(name = "Name", nullable = false, columnDefinition = "NVARCHAR(100)")
    private String name;

    @Column(name = "Email", nullable = false, length = 100)
    private String email;

    @Column(name = "Phone", nullable = false, length = 20)
    private String phone;

    @Column(name = "CreateAt", nullable = false)
    private Date createdAt;
}
