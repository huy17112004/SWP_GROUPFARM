package dto;

import java.math.BigDecimal;
import java.util.List;

public class ProductDashboardDTO {
    private int id;
    private String productName;
    private BigDecimal wholesalePrice;
    private String description;
    private List<String> imageUrl;
    private String categoryName;

    public ProductDashboardDTO(int id, String productName, BigDecimal wholesalePrice,String description, List<String> imageUrl, String categoryName) {
        this.id = id;
        this.productName = productName;
        this.wholesalePrice = wholesalePrice;
        this.description = description;
        this.imageUrl = imageUrl;
        this.categoryName = categoryName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getWholesalePrice() {
        return wholesalePrice;
    }

    public void setWholesalePrice(BigDecimal wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(List<String> imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
