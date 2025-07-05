package service;

import dao.WishlistDAO;
import entity.Wishlist;
import jakarta.persistence.EntityManager;
import util.JpaUtil;

import java.util.List;


public class WishlistService {

    public Wishlist addToWishlist(int customerId, int productId) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            WishlistDAO wishlistDAO = new WishlistDAO(em);
            em.getTransaction().begin();
            Wishlist wishlist = wishlistDAO.addToWishlist(customerId, productId);
            em.getTransaction().commit();
            return wishlist;
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

    public String removeFromWishlist(int customerId, int productId) {
        EntityManager em = JpaUtil.getEntityManager();
        WishlistDAO wishlistDAO = new WishlistDAO(em);
        try {
            em.getTransaction().begin();
            Wishlist wishlist = wishlistDAO.findByCustomerAndProduct(customerId, productId);
            if (wishlist == null) {
                throw new RuntimeException("Wishlist item not found for customerId=" + customerId + " and productId=" + productId);
            }
            em.remove(wishlist);
            em.getTransaction().commit();
            return "Product removed from wishlist successfully";
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

    public List<Wishlist> getWishlistByCustomerId(int customerId) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            WishlistDAO wishlistDAO = new WishlistDAO(em);
            return wishlistDAO.findByCustomerId(customerId);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public boolean isInWishlist(int customerId, int productId) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            WishlistDAO wishlistDAO = new WishlistDAO(em);
            return wishlistDAO.findByCustomerAndProduct(customerId, productId) != null;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
} 