package service;

import dao.CartDAO;
import dto.CartItemDTO;
import entity.Cart;
import jakarta.persistence.EntityManager;
import util.JpaUtil;


public class CartService {
    private final CartDAO cartDAO = new CartDAO();


    public Cart addToCart(CartItemDTO dto) {
        EntityManager em = JpaUtil.getEntityManager();
        try {

            em.getTransaction().begin();
            Cart cart = cartDAO.addToCart(dto,em);
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
        CartDAO cartDAO1 = new CartDAO();
        try {

            em.getTransaction().begin();
            Cart cart = cartDAO1.findByUserAndProduct(em, userId,productId);
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
}