package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.WholesaleOrderDAO;
import dto.OrderSellerDTO;
import entity.Shipper;
import entity.WarehouseStaff;
import entity.WholesaleOrder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.SellerOrderService;
import service.ShipperService;
import service.WarehouseStaffService;
import util.JpaUtil;
import util.LocalDateTimeAdapter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/api/shipper/orders")
public class ShipperOrderServlet extends HttpServlet {
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        try {
            HttpSession session = req.getSession(false);
            Integer userId = (session != null) ? (Integer) session.getAttribute("userId") : null;
            String accountType = (session != null) ? (String) session.getAttribute("accountType") : null;

            if (userId == null) {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                resp.getWriter().write(gson.toJson(new MessageResponse("Bạn chưa đăng nhập!", false)));
                return;
            }

            if (!"SHIPPER".equals(accountType)) {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                resp.getWriter().write(gson.toJson(new MessageResponse("Bạn không phải nhân viên giao hàng!", false)));
                return;
            }
            WholesaleOrderDAO orderDao = new WholesaleOrderDAO(JpaUtil.getEntityManager());
            List<WholesaleOrder> orders = orderDao.findAllByStatusAndShipper(userId, "SHIPPING");
            List<OrderSellerDTO> dtos = orders.stream()
                    .map(SellerOrderService::mapToOrderSellerDTO)
                    .collect(Collectors.toList());
            resp.getWriter().write(gson.toJson(dtos));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(gson.toJson("Error: " + e.getMessage()));
        }
    }
}
