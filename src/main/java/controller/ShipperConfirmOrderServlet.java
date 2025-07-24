package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.WholesaleOrderService;

import java.io.IOException;

@WebServlet("/api/shipper/confirm-order")
public class ShipperConfirmOrderServlet extends HttpServlet {
    private final Gson gson = new Gson();

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        WholesaleOrderService orderService = new WholesaleOrderService();
        orderService.confirmOrderByShipper(orderId);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
