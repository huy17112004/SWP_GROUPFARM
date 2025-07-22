package dto;

import entity.Wishlist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class WishListDTO {
    private Long customerId;
    private Long productId;
    private String productName;
    private String productImage;
    private BigDecimal productPrice;

    public WishListDTO(Long customerId, Long productId, String productName, String productImage, BigDecimal productPrice) {
        this.customerId = customerId;
        this.productId = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.productPrice = productPrice;

    }
}
