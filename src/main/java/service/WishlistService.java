package service;

import dao.WishlistDAO;
import dto.WishListDTO;
import entity.Cart;
import entity.Product;
import entity.Wishlist;
import jakarta.persistence.EntityManager;
import util.JpaUtil;

import java.util.ArrayList;
import java.util.List;


public class WishlistService {

    public Wishlist addToWishlist(WishListDTO dto) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            WishlistDAO wishlistDAO = new WishlistDAO(em);
            em.getTransaction().begin();
            Wishlist wishlist = wishlistDAO.addToWishlist(dto);
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

    public List<WishListDTO> getWishlistByCustomerId(int customerId) {
        EntityManager em = JpaUtil.getEntityManager();
        WishlistDAO wishlistDAO = new WishlistDAO(em);
        List<Wishlist> wishlistList = wishlistDAO.findByCustomerId(customerId);
        List<WishListDTO> result = new ArrayList<>();
        
        for (Wishlist wishlist : wishlistList) {
            Product p = wishlist.getProduct();
            if (p == null) continue;
            WishListDTO dto = new WishListDTO();
            dto.setCustomerId(Long.valueOf(wishlist.getCustomer().getId()));
            dto.setProductId((long) p.getId());
            dto.setProductName(p.getProductName());
            dto.setProductPrice(p.getWholesalePrice());
            if (p.getImages() != null && !p.getImages().isEmpty()) {
                dto.setProductImage(p.getImages().get(0).getImageUrl());
            }
            result.add(dto);
        }
        em.close();
        return result;
    }





}