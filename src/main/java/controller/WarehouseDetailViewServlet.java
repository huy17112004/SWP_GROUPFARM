package controller;

import com.google.gson.Gson;
import dto.WarehouseDetailViewDTO;
import service.WarehouseDetailViewService;
import service.WarehouseService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/warehouses-detail/*")
public class WarehouseDetailViewServlet extends HttpServlet {
    private final WarehouseDetailViewService warehouseService = new WarehouseDetailViewService();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String pathInfo = request.getPathInfo(); // null hoặc "/id"

        if (pathInfo != null && pathInfo.length() > 1) {
            // /{id} -> chi tiết một kho
            try {
                int id = Integer.parseInt(pathInfo.substring(1));
                WarehouseDetailViewDTO dto = warehouseService.getDetailViewById(id);
                if (dto == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write(gson.toJson(new MessageResponse("Not Found", false)));
                } else {
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().write(gson.toJson(dto));
                }
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write(gson.toJson(new MessageResponse("Bad Request", false)));
            }
            return;
        }
        // Không có id -> trả về list
        List<WarehouseDetailViewDTO> list = warehouseService.getAllDetailView();
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(gson.toJson(list));
    }
}
