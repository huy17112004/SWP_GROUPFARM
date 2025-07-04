package service;

import entity.Cart;
import jakarta.persistence.EntityManager;

import java.math.BigDecimal;
import java.util.List;

public class CartService {
    public BigDecimal calculateCartTotal(List<Cart> carts, EntityManager em) {
        return carts.stream()
                .map(cart -> {
                    BigDecimal price = cart.getProduct().getWholesalePrice(); // hoặc lấy từ DB
                    BigDecimal quantity = BigDecimal.valueOf(cart.getQuantity());
                    return price.multiply(quantity);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
