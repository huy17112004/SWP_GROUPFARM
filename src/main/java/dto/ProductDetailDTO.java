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
public class ProductDetailDTO {
    private int id;
    private String productName;
    private BigDecimal wholesalePrice;
    private String description;
    private List<String> imageUrl;
    private String categoryName;

    public ProductDetailDTO(Product product) {
        this.id = product.getId();
        this.productName = product.getProductName();
        this.wholesalePrice = product.getWholesalePrice();
        this.description = product.getDescription();
        this.categoryName = product.getCategory() != null ? product.getCategory().getCategoryName() : "";
        this.imageUrl = product.getImages() != null
                ? product.getImages().stream().map(img -> img.getImageUrl()).collect(java.util.stream.Collectors.toList())
                : new java.util.ArrayList<>();
    }
}