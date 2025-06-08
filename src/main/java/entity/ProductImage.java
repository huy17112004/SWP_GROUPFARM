package entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ProductImage")
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductImageID")
    private int productImageID;

    @Column(name = "ProductID", nullable = false)
    private int productID;

    @Column(name = "ImageUrl", length = 50, nullable = false)
    private String imageUrl;

    /* n ProductImage ↔ 1 Product */
    @ManyToOne
    @JoinColumn(name = "ProductID", nullable = false, insertable = false, updatable = false)
    private Product product;
}