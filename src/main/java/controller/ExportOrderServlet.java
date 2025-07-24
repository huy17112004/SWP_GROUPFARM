package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ShippingService;
import util.LocalDateTimeAdapter;

import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/api/export-order")
public class ExportOrderServlet extends HttpServlet {

    private final ShippingService shippingService = new ShippingService();
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        String orderIdRequest = request.getParameter("orderId");
        String shipperIdRequest = request.getParameter("shipperId");

        try {
            int orderId = Integer.parseInt(orderIdRequest);
            int shipperId = Integer.parseInt(shipperIdRequest);
            boolean success = shippingService.exportOrderToShipper(orderId, shipperId);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println(gson.toJson("Oke"));
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(gson.toJson("Lỗi"));
        }
    }
}
