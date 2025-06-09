package entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductID")
    private int id;

    @Column(name = "ProductName", length = 50, nullable = false)
    private String productName;

    @Column(name = "EntryPrice", nullable = false)
    private int entryPrice;

    @Column(name = "RetailPrice", nullable = false)
    private int retailPrice;

    @Column(name = "WholesalePrice", precision = 10, scale = 2, nullable = false)
    private BigDecimal wholesalePrice;

    @Column(name = "Description", length = 50)
    private String description;

    /* 1 Product ↔ n ProductImage */
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> images;

    /* 1 Product ↔ n Cart */
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cart> carts;

    /* 1 Product ↔ n WholesaleOrderItem */
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WholesaleOrderItem> wholesaleOrderItems;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StockLot> stockLots;

    /* 1 Category ↔ n Product */
    @ManyToOne
    @JoinColumn(name = "CategoryID", nullable = false, insertable = false, updatable = false)
    private Category category;
}