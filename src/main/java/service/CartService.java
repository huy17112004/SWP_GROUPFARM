package service;

import dao.CartDAO;
import dto.CartItemDTO;
import entity.Cart;
import entity.Product;
import jakarta.persistence.EntityManager;
import util.JpaUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CartService {


    public Cart addToCart(CartItemDTO dto) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            CartDAO cartDAO = new CartDAO(em);  
            em.getTransaction().begin();
            Cart cart = cartDAO.addToCart(dto);
            em.getTransaction().commit();
            return cart;
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public List<CartItemDTO> getCartByUserId(Long userId) {
        EntityManager em = JpaUtil.getEntityManager();
        CartDAO cartDAO = new CartDAO(em);
        List<Cart> cartList = cartDAO.findByUserId(userId);
        List<CartItemDTO> result = new ArrayList<>();

        for (Cart cart : cartList) {
            Product p = cart.getProduct();
            if (p == null) continue;

            CartItemDTO dto = new CartItemDTO();
            dto.setProductId(Long.valueOf(p.getId()));
            dto.setProductName(p.getProductName());
            dto.setWholesalePrice(p.getWholesalePrice());
            dto.setQuantity(cart.getQuantity());

            result.add(dto);
        }

        em.close();
        return result;
    }


    public String removeFromCart(Long productId, long userId) {
        EntityManager em = JpaUtil.getEntityManager();
        CartDAO cartDAO = new CartDAO(em);
        try {
            em.getTransaction().begin();
            
            Cart cart = cartDAO.findByUserAndProduct(userId, productId);
            if (cart == null) {
                throw new RuntimeException("Cart item not found for userId=" + userId + " and productId=" + productId);
            }
            
            // Remove cart item
            em.remove(cart);
            em.getTransaction().commit();
            return "Product removed from cart successfully";

        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public BigDecimal calculateCartTotal(List<Cart> carts, EntityManager em) {
        return carts.stream()
                .map(cart -> {
                    BigDecimal price = cart.getProduct().getWholesalePrice(); // hoặc lấy từ DB
                    BigDecimal quantity = BigDecimal.valueOf(cart.getQuantity());
                    return price.multiply(quantity);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public boolean updateQuantity(Long userId, Long productId, int quantity) {
        EntityManager em = JpaUtil.getEntityManager();
        CartDAO cartDAO = new CartDAO(em);
        try {
            em.getTransaction().begin();

            Cart cart = cartDAO.findByUserAndProduct(userId, productId);
            if (cart == null) {
                throw new RuntimeException("Không tìm thấy sản phẩm trong giỏ hàng.");
            }

            if (quantity <= 0) {
                em.remove(cart);
            } else {
                cart.setQuantity(quantity);
            }

            em.getTransaction().commit();
            return true;
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}