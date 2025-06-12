package dto;

import entity.Category;
import entity.Product;
import entity.ProductImage;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {
    private String productName;
    private int retailPrice;
    private BigDecimal wholesalePrice;
    private String description;
    private List<String> imageUrls;
    private String categoryName;

    public ProductResponseDTO(Product product) {
        this.productName = product.getProductName();
        this.retailPrice = product.getRetailPrice();
        this.wholesalePrice = product.getWholesalePrice();
        this.description = product.getDescription();

        // map hình ảnh (giả sử có thuộc tính getImageUrl() trong ProductImage)
        this.imageUrls = product.getImages().stream()
                .map(ProductImage::getImageUrl)
                .collect(Collectors.toList());

        this.categoryName = product.getCategory().getCategoryName();
    }
}
