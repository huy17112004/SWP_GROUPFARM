package dao;

import dto.CartItemDTO;
import entity.Cart;
import entity.Product;
import entity.WholesaleCustomer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.io.Serializable;
import java.util.List;

public class CartDAO extends GenericDAO<Cart> {
    public CartDAO(EntityManager entityManager) {

        super(Cart.class, entityManager);
    }
    public Cart addToCart(CartItemDTO dto) {

        WholesaleCustomer customer = em.find(WholesaleCustomer.class, dto.getUserId().intValue());
        Product product = em.find(Product.class, dto.getProductId().intValue());

        if (customer == null || product == null) {
            throw new RuntimeException("Customer or Product not found");
        }

        // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
        Cart existingCart = findByUserAndProduct(dto.getUserId(), dto.getProductId());
        Cart cart;

        if (existingCart != null) {

            existingCart.setQuantity(existingCart.getQuantity() + dto.getQuantity());
            em.merge(existingCart);
            cart = existingCart;
        } else {

            cart = new Cart();
            cart.setCustomer(customer);
            cart.setProduct(product);
            cart.setQuantity(dto.getQuantity());
            em.persist(cart);
        }

        return cart;
    }

    public List<Cart> findAllByCustomerId(int customerId) {
        List<Cart> list = em.createQuery(
                        "SELECT DISTINCT c FROM Cart c " +
                                "LEFT JOIN FETCH c.product " +
                                "WHERE c.customer.id = :customerId", Cart.class)
                .setParameter("customerId", customerId)
                .getResultList();
        return list;
    }

    public void deleteByCustomerId(int customerId) {
        em.createQuery("DELETE FROM Cart c WHERE c.customer.id = :customerId")
                .setParameter("customerId", customerId)
                .executeUpdate();
    }


    public Cart findByUserAndProduct(Long userId, Long productId) {
        try {
            WholesaleCustomer customer = em.find(WholesaleCustomer.class, userId.intValue());
            Product product = em.find(Product.class, productId.intValue());
            return em.createQuery("SELECT c FROM Cart c WHERE c.customer = :customer AND c.product = :product", Cart.class)
                    .setParameter("customer", customer)
                    .setParameter("product", product)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Cart> findByUserId(Long userId) {
        return em.createQuery(
                        "SELECT c FROM Cart c " +
                                "JOIN FETCH c.product p " +
                                "LEFT JOIN FETCH p.images " +
                                "WHERE c.customer.id = :userId", Cart.class)
                .setParameter("userId", userId.intValue())
                .getResultList();
    }

    

}