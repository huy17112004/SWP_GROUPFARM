package controller;

import com.google.gson.Gson;
import dto.OrderSellerDTO;
import entity.WholesaleOrder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.SellerOrderService;
import util.JpaUtil;
import dao.WholesaleOrderDAO;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/api/manager/orders")
public class ManagerOrderServlet extends HttpServlet {
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        try {
            WholesaleOrderDAO orderDao = new WholesaleOrderDAO(JpaUtil.getEntityManager());
            List<WholesaleOrder> orders = orderDao.findAllByStatusWithItems("CONFIRMED");
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