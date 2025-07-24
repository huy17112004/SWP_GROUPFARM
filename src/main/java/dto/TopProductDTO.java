package dto;

import java.math.BigDecimal;

public class TopProductDTO {
    private int productId;
    private String productName;
    private int totalQuantitySold;
    private BigDecimal totalRevenue;
    private String imageUrl;
    private int rank;
    private BigDecimal price;
    
    public TopProductDTO() {
    }

    public TopProductDTO(int productId, String productName, int totalQuantitySold, BigDecimal totalRevenue, String imageUrl, int rank, BigDecimal price) {
        this.productId = productId;
        this.productName = productName;
        this.totalQuantitySold = totalQuantitySold;
        this.totalRevenue = totalRevenue;
        this.imageUrl = imageUrl;
        this.rank = rank;
        this.price = price;
    }
    
    // Getters and Setters
    public int getProductId() {
        return productId;
    }
    
    public void setProductId(int productId) {
        this.productId = productId;
    }
    
    public String getProductName() {
        return productName;
    }
    
    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    public int getTotalQuantitySold() {
        return totalQuantitySold;
    }
    
    public void setTotalQuantitySold(int totalQuantitySold) {
        this.totalQuantitySold = totalQuantitySold;
    }
    
    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }
    
    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public int getRank() {
        return rank;
    }
    
    public void setRank(int rank) {
        this.rank = rank;
    }

    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
