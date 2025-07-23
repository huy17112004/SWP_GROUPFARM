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
@Table(name = "Shipper")
public class  Shipper extends Account {

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "CreateAt")
    private Date createdAt;

    @Column(name = "Status")
    private String status;

    @OneToMany(mappedBy = "shipper", fetch = FetchType.LAZY)
    private List<ShippingLog> shippingLogs;

    // Getters & Setters...
}
