package entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "WholesaleOrderItem")
public class WholesaleOrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ItemID")
    private int id;

    @Column(name = "Quantity", nullable = false)
    private int quantity;

    @Column(name = "Price", precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    @Column(name = "SubTotal", precision = 10, scale = 2, nullable = false)
    private BigDecimal subTotal;

    /* n WholesaleOrderItem ↔ 1 WholesaleOrder */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OrderID", nullable = false, insertable = false, updatable = false)
    private WholesaleOrder order;

    /* n WholesaleOrderItem ↔ 1 Product */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProductID", nullable = false, insertable = false, updatable = false)
    private Product product;
}