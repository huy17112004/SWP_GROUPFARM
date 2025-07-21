package controller;

import com.google.gson.Gson;
import dto.WarehouseDetailRequestDTO;
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
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET ,POST, DELETE, PUT, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String pathInfo = request.getPathInfo();

        if (pathInfo != null && pathInfo.length() > 1) {
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
        List<WarehouseDetailViewDTO> list = warehouseService.getAllDetailView();
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(gson.toJson(list));
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("application/json");
        WarehouseDetailRequestDTO dto = gson.fromJson(req.getReader(), WarehouseDetailRequestDTO.class);
        boolean ok = warehouseService.createDetail(dto);
        resp.setStatus(ok ? HttpServletResponse.SC_CREATED : HttpServletResponse.SC_BAD_REQUEST);
        resp.getWriter().write(gson.toJson(new MessageResponse(ok ? "Created" : "Failed", ok)));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("application/json");
        String path = req.getPathInfo();
        if (path == null || path.length()<=1) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(gson.toJson(new MessageResponse("Missing ID", false)));
            return;
        }
        int id = Integer.parseInt(path.substring(1));
        WarehouseDetailRequestDTO dto = gson.fromJson(req.getReader(), WarehouseDetailRequestDTO.class);
        boolean ok = warehouseService.updateDetail(id, dto);
        resp.setStatus(ok ? HttpServletResponse.SC_OK : HttpServletResponse.SC_NOT_FOUND);
        resp.getWriter().write(gson.toJson(new MessageResponse(ok ? "Updated" : "Not Found", ok)));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("application/json");
        String path = req.getPathInfo();
        if (path==null || path.length()<=1) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(gson.toJson(new MessageResponse("Missing ID", false)));
            return;
        }
        int id = Integer.parseInt(path.substring(1));
        boolean ok = warehouseService.deleteDetail(id);
        resp.setStatus(ok ? HttpServletResponse.SC_NO_CONTENT : HttpServletResponse.SC_NOT_FOUND);
        if (!ok) {
            resp.getWriter().write(gson.toJson(new MessageResponse("Not Found", false)));
        }
    }
}
