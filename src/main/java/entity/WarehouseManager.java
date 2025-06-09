package entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "WarehouseManager")
public class WarehouseManager extends Account {

    @OneToOne(mappedBy = "warehouseManager",fetch = FetchType.LAZY)
    private Warehouse warehouse;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Email", nullable = false, length = 100)
    private String email;

    @Column(name = "Phone", nullable = false, length = 20)
    private String phone;

    @Column(name = "CreateAt", nullable = false)
    private Date createdAt;
}
