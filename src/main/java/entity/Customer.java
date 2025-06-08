package entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CustomerID")
    private int customerID;

    /* 1 Customer ↔ n Cart */
    @OneToMany(mappedBy = "customerID", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cart> carts;

    /* 1 Customer ↔ n Contract */
    @OneToMany(mappedBy = "customerID", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contract> contracts;
}