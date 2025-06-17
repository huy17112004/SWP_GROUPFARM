package entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ShippingRequirement")
public class ShippingRequirement {

    @Id
    @Column(name = "ProductID")
    private int productId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId // dùng productId làm cả khóa chính và khóa ngoại
    @JoinColumn(name = "ProductID")
    private Product product;

    @Column(name = "MinExpiryDaysRequired", nullable = false)
    private int minExpiryDaysRequired;

    @Column(name = "RatePerKmPerKg", nullable = false)
    private int ratePerKmPerKg;
}
