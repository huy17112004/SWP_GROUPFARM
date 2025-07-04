package entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Seller")
public class Seller extends Account {

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Email", nullable = false, length = 100)
    private String email;

    @Column(name = "Phone", nullable = false, length = 20)
    private String phone;

    @Column(name = "CreateAt", nullable = false)
    private Date createdAt;

    @Column(name = "Status", nullable = false, length = 50)
    private String status;

    @OneToMany(mappedBy = "seller", fetch = FetchType.LAZY)
    private List<WholesaleOrder> wholesaleOrders;
    // Getters & Setters...
}
