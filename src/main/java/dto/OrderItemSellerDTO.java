package dto;

import java.math.BigDecimal;

public class OrderItemSellerDTO {
    private int itemId;
    private String productName;
    private String productImage;
    private BigDecimal price;
    private int quantity;
    private BigDecimal subTotal;

    public OrderItemSellerDTO() {
    }

    public OrderItemSellerDTO(int itemId, String productName, String productImage, 
                             BigDecimal price, int quantity, BigDecimal subTotal) {
        this.itemId = itemId;
        this.productName = productName;
        this.productImage = productImage;
        this.price = price;
        this.quantity = quantity;
        this.subTotal = subTotal;
    }

    // Getters and Setters
    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }
} 