package controller;

import com.google.gson.Gson;
import dto.ShipperNameDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ShipperService;
import service.ShippingService;

import java.io.IOException;
import java.util.List;

@WebServlet("/shippers")
public class ShipperServlet extends HttpServlet {
    private final ShipperService shipperService = new ShipperService();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ShipperNameDTO> dtos = shipperService.getShippers();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(gson.toJson(dtos));
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
