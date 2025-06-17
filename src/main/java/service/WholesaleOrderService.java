package service;

import dao.CartDAO;
import dao.WholesaleOrderDAO;
import dto.PlaceOrderRequestDTO;
import entity.Cart;
import entity.WholesaleOrder;
import entity.WholesaleOrderItem;
import jakarta.persistence.EntityManager;
import util.JpaUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WholesaleOrderService {

    public int placeOrder(PlaceOrderRequestDTO dto) {
        // 1. Lấy cart
        EntityManager em = JpaUtil.getEntityManager();
        try {
            CartDAO cartDAO = new CartDAO(em);
            WholesaleOrderDAO wholesaleOrderDAO = new WholesaleOrderDAO(em);
            List<Cart> cartList = cartDAO.findAllByCustomerId(dto.getCustomerId());
            if (cartList == null || cartList.isEmpty()) throw new RuntimeException("Cart is empty");

            // 2. Tạo order và item (giống phần trên)
            WholesaleOrder order = new WholesaleOrder();
            order.setStatus("PENDING");
            order.setEstimatedShipFee(dto.getEstimatedShipFee());
            order.setCreatedAt(new Date());
            BigDecimal totalPrice = BigDecimal.ZERO;

            List<WholesaleOrderItem> orderItems = new ArrayList<>();
            for (Cart cart : cartList) {
                WholesaleOrderItem item = new WholesaleOrderItem();
                item.setProduct(cart.getProduct());
                item.setQuantity(cart.getQuantity());
                item.setPrice(cart.getProduct().getWholesalePrice());
                BigDecimal subTotal = cart.getProduct().getWholesalePrice().multiply(BigDecimal.valueOf(cart.getQuantity()));
                item.setSubTotal(subTotal);
                item.setOrder(order);
                orderItems.add(item);
                totalPrice = totalPrice.add(subTotal);
            }
            order.setItems(orderItems);
            order.setTotalPrice(totalPrice);

            // 3. Lưu order và item (cascade)
            wholesaleOrderDAO.save(order);

            // 4. Xóa cart
            cartDAO.deleteByCustomerId(dto.getCustomerId());

            return order.getId();

        } finally {
            em.close();
        }

    }
}
