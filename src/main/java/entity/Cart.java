package entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CartID")
    private int id;

    @Column(name = "Quantity")
    private int quantity;

    /* n Cart ↔ 1 Customer */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CustomerID", nullable = false, insertable = false, updatable = false)
    private WholesaleCustomer customer;

    /* n Cart ↔ 1 Product */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProductID", nullable = false, insertable = false, updatable = false)
    private Product product;
}