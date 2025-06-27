package controller;

import com.google.gson.Gson;
import dto.WarehouseRequestDTO;
import entity.Warehouse;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.WarehouseService;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/api/warehouses")
public class WarehouseServlet extends HttpServlet {
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Đọc JSON
        BufferedReader reader = request.getReader();
        WarehouseRequestDTO dto = gson.fromJson(reader, WarehouseRequestDTO.class);

        response.setContentType("application/json");

        // 3. Gọi service
        WarehouseService warehouseService = new WarehouseService();
        try {
            warehouseService.addWarehouse(dto);
            response.setStatus(HttpServletResponse.SC_CREATED);
            String json = gson.toJson(new MessageResponse("Success", true));
            response.getWriter().write(json);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            String json = gson.toJson(new MessageResponse("Error", false));
            response.getWriter().write(json);
        }
    }
}
