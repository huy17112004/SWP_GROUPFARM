package dto;

import entity.Product;
import entity.ProductImage;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ProductResponseDTO {
    private int productId;
    private String productName;
    private int entryPrice;
    private int retailPrice;
    private BigDecimal wholesalePrice;
    private String imageUrl;
    private String categoryName;
    private String description;

    public ProductResponseDTO(Product product) {
        this.productId = product.getId();
        this.productName = product.getProductName() != null ? product.getProductName() : "";
        this.retailPrice = product.getRetailPrice();
        this.wholesalePrice = product.getWholesalePrice();

        // Lấy image đầu tiên
        if (product.getImages() != null && !product.getImages().isEmpty()) {
            this.imageUrl = product.getImages().get(0).getImageUrl();
        } else {
            this.imageUrl = "";
        }

        // Category
        if (product.getCategory() != null) {
            this.categoryName = product.getCategory().getCategoryName() != null ?
                    product.getCategory().getCategoryName() : "";
        } else {
            this.categoryName = "";
        }
        this.description = product.getDescription() != null ? product.getDescription() : "";
    }
}
