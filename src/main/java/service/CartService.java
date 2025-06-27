package service;

import dao.CartDAO;
import dto.CartItemDTO;
import entity.Cart;
import jakarta.persistence.EntityManager;
import util.JpaUtil;

import java.math.BigDecimal;
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


    public String removeFromCart(Long productId, long userId) {
        EntityManager em = JpaUtil.getEntityManager();
        CartDAO cartDAO1 = new CartDAO(em);
        try {

            em.getTransaction().begin();
            Cart cart = cartDAO1.findByUserAndProduct(userId,productId);
            if (cart == null) {
                throw new RuntimeException("Cart item not found for userId=" + userId + " and productId=" + productId);
            }
            cart = em.merge(cart);
            em.remove(cart);
            // Nếu không có cart nào được xóa thì báo lỗi
            em.getTransaction().commit();
            return "Deleted product";


        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            throw e;
        }finally {
            if (em.isOpen()) {
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
}