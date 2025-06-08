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
    private int cartID;
    
    private int customerID;
    private int productID;
    private int quantity;

    /* n Cart ↔ 1 Customer */
    @ManyToOne
    @JoinColumn(name = "CustomerID", nullable = false, insertable = false, updatable = false)
    private Customer customer;

    /* n Cart ↔ 1 Product */
    @ManyToOne
    @JoinColumn(name = "ProductID", nullable = false, insertable = false, updatable = false)
    private Product product;
}