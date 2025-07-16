//package service;
//
//import dao.*;
//import dto.OrderCustomerDTO;
//import dto.OrderItemCustomerDTO;
//import dto.OrderResponseDTO;
//import entity.*;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.EntityTransaction;
//import util.JpaUtil;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.stream.Collectors;
//
//
//public class WholesaleOrderService {
//
//    public OrderResponseDTO placeOrder(
//            int customerId,
//            LocalDateTime deliveryDate,
//            int addressId,
//            double avgSpeedKmph
//    ) {
//        EntityManager em = JpaUtil.getEntityManager();
//        EntityTransaction tx = em.getTransaction();
//
//
//        OrderResponseDTO resp = new OrderResponseDTO();
//        try {
//            CartDAO cartDAO = new CartDAO(em);
//            WarehouseDAO warehouseDAO = new WarehouseDAO(em);
//            WholesaleOrderDAO orderDAO = new WholesaleOrderDAO(em);
//            StockLotDAO stockLotDAO = new StockLotDAO(em);
//            AddressDAO addressDAO = new AddressDAO(em);
//            SellerService  sellerService = new SellerService();
//            ShippingService shippingService = new ShippingService();
//            WholesaleCustomerDAO customerDAO = new WholesaleCustomerDAO(em);
//
//            List<Cart> carts = cartDAO.findAllByCustomerId(customerId);
//            if (carts.isEmpty()) {
//                resp.setSuccess(false);
//                resp.setMessage("Giỏ hàng trống.");
//                return resp;
//            }
//
//            // 1. Tìm kho đích
//            Address deliveryAddress = addressDAO.findById(addressId);
//            float latitude =  deliveryAddress.getLatitude();
//            float longitude = deliveryAddress.getLongitude();
//            List<Warehouse> warehouses = warehouseDAO.findAll();
//            Warehouse destWarehouse = shippingService.findNearestWarehouseOnTime(
//                    warehouses, latitude, longitude, deliveryDate, avgSpeedKmph
//            );
//            if (destWarehouse == null) {
//                resp.setSuccess(false);
//                resp.setMessage("Địa chỉ quá xa, không thể giao hàng đúng hạn!");
//                return resp;
//            }
//
//            // 2. Kiểm tra tồn kho từng sản phẩm
//            List<OrderResponseDTO.ProductStockInfo> lackList = new ArrayList<>();
//            Date deliveryDateSql = java.sql.Timestamp.valueOf(deliveryDate);
//            for (Cart c : carts) {
//                List<StockLot> lots = stockLotDAO.findEligibleStockLotsJava(c.getProduct().getId(), deliveryDateSql);
//                int total = lots.stream().mapToInt(lot -> {
//                    int allocated = lot.getOrderItemAllocations() == null ? 0 :
//                            lot.getOrderItemAllocations().stream().filter(l -> "RESERVED".equals(l.getStatus()))
//                                    .mapToInt(OrderItemAllocation::getQuantity).sum();
//                    return lot.getQuantity() - allocated;
//                }).sum();
//                if (total < c.getQuantity()) {
//                    OrderResponseDTO.ProductStockInfo info = new OrderResponseDTO.ProductStockInfo();
//                    info.setProductId(c.getProduct().getId());
//                    info.setProductName(c.getProduct().getProductName());
//                    info.setRequired(c.getQuantity());
//                    info.setAvailable(total);
//                    lackList.add(info);
//                }
//            }
//            if (!lackList.isEmpty()) {
//                resp.setSuccess(false);
//                resp.setMessage("Thiếu sản phẩm trong kho.");
//                resp.setStockDetails(lackList);
//                return resp;
//            }
//
//            // 3. Đủ điều kiện -> lên đơn
//            tx.begin();
//            double distanceKm = shippingService.haversine(
//                    destWarehouse.getAddress().getLatitude(),
//                    destWarehouse.getAddress().getLongitude(),
//                    latitude, longitude);
//
//            CartService cartService = new CartService();
//            WholesaleOrder wholesaleOrder = new WholesaleOrder();
//            wholesaleOrder.setStatus("CREATED");
//            wholesaleOrder.setCreatedAt(new Date());
//            wholesaleOrder.setCustomer(customerDAO.findById(customerId));
//            wholesaleOrder.setDeliveryDate(deliveryDate);
//
//
//            // set ship
//            wholesaleOrder.setEstimatedShipFee(
//                    shippingService.calculateShippingFee(distanceKm, carts, em));
//
//            wholesaleOrder.setItemsTotal(cartService.calculateCartTotal(carts, em));
//
//            // set total price before deal
//            wholesaleOrder.setTotalPrice(
//                    wholesaleOrder.getEstimatedShipFee().add(cartService.calculateCartTotal(carts, em)));
//
//            // set source warehouse
//            wholesaleOrder.setSourceWarehouse(destWarehouse);
//
//            List<WholesaleOrderItem> wholesaleOrderItems = new ArrayList<>();
//            for (Cart c : carts) {
//                WholesaleOrderItem wholesaleOrderItem = new WholesaleOrderItem();
//                wholesaleOrderItem.setProduct(c.getProduct());
//                wholesaleOrderItem.setQuantity(c.getQuantity());
//                wholesaleOrderItem.setPrice(c.getProduct().getWholesalePrice());
//                wholesaleOrderItem.setSubTotal(
//                        wholesaleOrderItem.getPrice().multiply(BigDecimal.valueOf(wholesaleOrderItem.getQuantity())));
//                wholesaleOrderItem.setOrder(wholesaleOrder);
//                int quantityNeeded = c.getQuantity();
//                List<StockLot> lots = stockLotDAO.findEligibleStockLotsJava(c.getProduct().getId(), deliveryDateSql);
//                List<OrderItemAllocation> orderItemAllocations = new ArrayList<>();
//                for (StockLot lot : lots) {
//                    OrderItemAllocation allocation = new OrderItemAllocation();
//                    allocation.setQuantity(Math.min(quantityNeeded, lot.getQuantity()));
//                    allocation.setStockLot(lot);
//                    allocation.setOrderItem(wholesaleOrderItem);
//                    allocation.setStatus("RESERVED");
//                    orderItemAllocations.add(allocation);
//                    if (quantityNeeded > lot.getQuantity()) {
//                        quantityNeeded -= lot.getQuantity();
//                    } else {
//                        quantityNeeded = 0;
//                        break;
//                    }
//                }
//                wholesaleOrderItem.setOrderItemAllocations(orderItemAllocations);
//                wholesaleOrderItems.add(wholesaleOrderItem);
//            }
//            wholesaleOrder.setItems(wholesaleOrderItems);
//
//            // get seller
//            Seller seller = sellerService.selectBestSeller(em);
//            wholesaleOrder.setSeller(seller);
//
//            wholesaleOrder.setDeliveryAddress(deliveryAddress);
//
//            orderDAO.save(wholesaleOrder);
//            cartDAO.deleteByCustomerId(customerId);
//
//            tx.commit();
//
//            resp.setSuccess(true);
//            resp.setMessage("Đặt hàng thành công!");
//            resp.setOrderId(wholesaleOrder.getId());
//            return resp;
//        } catch (Exception e) {
//            if (tx.isActive()) {
//                tx.rollback();
//            }
//            throw e;
//        } finally {
//            if (em.isOpen()) {
//                em.close();
//            }
//        }
//    }
//
//    public OrderCustomerDTO getOrderForCustomer(int orderId) {
//        EntityManager em = JpaUtil.getEntityManager();
//        try {
//            WholesaleOrderDAO orderDao = new WholesaleOrderDAO(em);
//            WholesaleOrder order = orderDao.findByIdWithItems(orderId);
//            if (order == null) {
//                throw new IllegalArgumentException("Order không tồn tại: id=" + orderId);
//            }
//
//            // Map từng WholesaleOrderItem → DTO
//            List<OrderItemCustomerDTO> items = order.getItems().stream()
//                    .map(this::mapItem)
//                    .collect(Collectors.toList());
//
//            // Tính tổng đơn
//            BigDecimal total = items.stream()
//                    .map(OrderItemCustomerDTO::getSubTotal)
//                    .reduce(BigDecimal.ZERO, BigDecimal::add);
//
//            return new OrderCustomerDTO(
//                    order.getId(),
//                    items,
//                    total,
//                    order.getEstimatedShipFee(),
//                    order.getStatus()
//            );
//        } finally {
//            em.close();
//        }
//    }
//
//    private OrderItemCustomerDTO mapItem(WholesaleOrderItem item) {
//        return new OrderItemCustomerDTO(
//                item.getId(),
//                item.getProduct().getProductName(),
//                item.getPrice(),
//                item.getQuantity(),
//                item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))
//        );
//    }
//
//    public List<OrderCustomerDTO> getAllOrdersForCustomer(int customerId) {
//        EntityManager em = JpaUtil.getEntityManager();
//        try {
//            WholesaleOrderDAO orderDao = new WholesaleOrderDAO(em);
//            List<WholesaleOrder> orders = orderDao.findAllByCustomerIdWithItems(customerId);
//            return orders.stream().map(order -> {
//                List<OrderItemCustomerDTO> items = order.getItems().stream()
//                        .map(this::mapItem)
//                        .collect(Collectors.toList());
//                BigDecimal total = items.stream()
//                        .map(OrderItemCustomerDTO::getSubTotal)
//                        .reduce(BigDecimal.ZERO, BigDecimal::add);
//                return new OrderCustomerDTO(
//                        order.getId(),
//                        items,
//                        total,
//                        order.getEstimatedShipFee(),
//                        order.getStatus()
//                );
//            }).collect(Collectors.toList());
//        } finally {
//            em.close();
//        }
//    }
//
//    public boolean confirmOrder(int orderId, int customerId) {
//        EntityManager em = JpaUtil.getEntityManager();
//        EntityTransaction tx = em.getTransaction();
//        try {
//            tx.begin();
//
//            WholesaleOrderDAO orderDao = new WholesaleOrderDAO(em);
//            DealRequestDAO dealDao = new DealRequestDAO(em);
//
//            WholesaleOrder order = orderDao.findById(orderId);
//            if (order == null) {
//                throw new IllegalArgumentException("Đơn hàng không tồn tại");
//            }
//
//            // Kiểm tra xem order có thuộc về customer này không
//            if (order.getCustomer().getId() != customerId) {
//                throw new IllegalArgumentException("Bạn không có quyền xác nhận đơn hàng này");
//            }
//
//            // Kiểm tra xem order có status NEGOTIATING không
//            if (!"NEGOTIATING".equals(order.getStatus())) {
//                throw new IllegalStateException("Chỉ có thể xác nhận đơn hàng đang trong giai đoạn thương lượng");
//            }
//
//            // Kiểm tra xem có deal nào đang PENDING không
//            List<DealRequest> pendingDeals = new ArrayList<>();
//            for (WholesaleOrderItem item : order.getItems()) {
//                List<DealRequest> itemDeals = dealDao.findByOrderItemId(item.getId());
//                for (DealRequest deal : itemDeals) {
//                    if ("PENDING".equals(deal.getStatus())) {
//                        pendingDeals.add(deal);
//                    }
//                }
//            }
//
//            if (!pendingDeals.isEmpty()) {
//                throw new IllegalStateException("Có " + pendingDeals.size() + " deal đang chờ xử lý. Vui lòng xử lý hết các deal trước khi xác nhận đơn hàng.");
//            }
//
//            // Cập nhật status thành DEPOSIT
//            order.setStatus("DEPOSIT");
//            order.setDealCompletedAt(new Date());
//
//            orderDao.update(order);
//            tx.commit();
//            return true;
//
//        } catch (Exception e) {
//            if (tx.isActive()) {
//                tx.rollback();
//            }
//            throw e;
//        } finally {
//            em.close();
//        }
//    }
//
//}