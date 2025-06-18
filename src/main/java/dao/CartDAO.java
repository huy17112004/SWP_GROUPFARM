package dao;

import dto.CartItemDTO;
import entity.Cart;
import entity.Product;
import entity.WholesaleCustomer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import util.JpaUtil;

public class CartDAO {


    public Cart addToCart(CartItemDTO dto, EntityManager em) {
        // Tìm customer và product từ cơ sở dữ liệu
        WholesaleCustomer customer = em.find(WholesaleCustomer.class, dto.getUserId().intValue());
        Product product = em.find(Product.class, dto.getProductId().intValue());

        if (customer == null || product == null) {
            throw new RuntimeException("Customer or Product not found");
        }

        // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
        Cart existingCart = findByUserAndProduct(em, dto.getUserId(), dto.getProductId());
        Cart cart;

        if (existingCart != null) {
            // Nếu đã có thì cộng thêm số lượng
            existingCart.setQuantity(existingCart.getQuantity() + dto.getQuantity());
            em.merge(existingCart);
            cart = existingCart;
        } else {
            // Nếu chưa có thì tạo mới cart item
            cart = new Cart();
            cart.setCustomer(customer);
            cart.setProduct(product);
            cart.setQuantity(dto.getQuantity());
            em.persist(cart);
        }

        return cart;
    }




//    public Cart removeFromCart(int productId, long userId, EntityManager em) {
//            Cart existingCart = findByUserAndProduct(em, userId, (long) productId);
//        if (existingCart != null) {
//            em.remove(existingCart);
//        }
//        return existingCart;
//    }


    public Cart findByUserAndProduct(EntityManager em, Long userId, Long productId) {
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
}