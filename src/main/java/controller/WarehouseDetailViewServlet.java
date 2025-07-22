package controller;

import com.google.gson.Gson;
import dto.WarehouseCreateDTO;
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
    private final WarehouseService service = new WarehouseService();
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("application/json;charset=UTF-8");

        WarehouseCreateDTO dto = gson.fromJson(req.getReader(), WarehouseCreateDTO.class);
        try {
            new WarehouseService().createFromDTO(dto);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write(gson.toJson(new MessageResponse("Warehouse created", true)));
        }
        catch (IllegalStateException dup) {
            // Trùng tên hoặc phone
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
            resp.getWriter().write(gson.toJson(new MessageResponse(dup.getMessage(), false)));
        }
        catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(gson.toJson(new MessageResponse(e.getMessage(), false)));
        }
    }
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("application/json;charset=UTF-8");

        String path = req.getPathInfo();  // "/{id}"
        if (path == null || path.length() < 2) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(gson.toJson(new MessageResponse("Missing warehouse ID", false)));
            return;
        }

        int id = Integer.parseInt(path.substring(1));
        WarehouseCreateDTO dto = gson.fromJson(req.getReader(), WarehouseCreateDTO.class);

        try {
            service.updateWarehouse(id, dto);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(gson.toJson(new MessageResponse("Warehouse updated", true)));
        } catch (IllegalStateException ex) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write(gson.toJson(new MessageResponse(ex.getMessage(), false)));
        } catch (Exception ex) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(gson.toJson(new MessageResponse(ex.getMessage(), false)));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("application/json;charset=UTF-8");
        String path = req.getPathInfo();  // "/{id}"
        if (path == null || path.length() < 2) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(gson.toJson(new MessageResponse("Missing warehouse ID", false)));
            return;
        }

        int id = Integer.parseInt(path.substring(1));
        try {
            service.deleteWarehouse(id);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (IllegalStateException ex) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write(gson.toJson(new MessageResponse(ex.getMessage(), false)));
        } catch (Exception ex) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(gson.toJson(new MessageResponse(ex.getMessage(), false)));
        }
    }

}
