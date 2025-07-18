package dao;

import dto.WishListDTO;
import entity.Account;
import entity.Product;
import entity.WholesaleCustomer;
import entity.Wishlist;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.List;

public class WishlistDAO {
    private final EntityManager em;

    public WishlistDAO(EntityManager em) {
        this.em = em;
    }

    public Wishlist addToWishlist(WishListDTO dto) {
        WholesaleCustomer customer = em.find(WholesaleCustomer.class, dto.getCustomerId().intValue());
        Product product = em.find(Product.class, dto.getProductId().intValue());

        if (customer == null || product == null) {
            throw new RuntimeException("Customer or Product not found");
        }

        Wishlist existing = findByCustomerAndProduct(dto.getCustomerId().intValue(), dto.getProductId().intValue());
        if (existing != null) {
            return existing;
        }

        Wishlist wishlist = new Wishlist();
        wishlist.setCustomer(customer);
        wishlist.setProduct(product);
        em.persist(wishlist);
        return wishlist;
    }

    public void removeFromWishlist(int customerId, int productId) {
        Wishlist wishlist = findByCustomerAndProduct(customerId, productId);
        if (wishlist != null) {
            em.remove(wishlist);
        }
    }

    public List<Wishlist> findByCustomerId(int customerId) {
        return em.createQuery(
                        "SELECT w FROM Wishlist w WHERE w.customer.id = :customerId",
                        Wishlist.class)
                .setParameter("customerId", customerId)
                .getResultList();
    }

    public List<Wishlist> findAll() {
        return em.createQuery("SELECT w FROM Wishlist w", Wishlist.class)
                .getResultList();
    }

    public Wishlist findByCustomerAndProduct(int customerId, int productId) {
        try {
            WholesaleCustomer customer = em.find(WholesaleCustomer.class, customerId);
            Product product = em.find(Product.class, productId);
            return em.createQuery(
                            "SELECT w FROM Wishlist w WHERE w.customer = :customer AND w.product = :product",
                            Wishlist.class)
                    .setParameter("customer", customer)
                    .setParameter("product", product)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }



}