package controller;

import com.google.gson.Gson;
import dto.WarehouseStaffRequestDTO;
import dto.WarehouseStaffListDTO;
import service.WarehouseStaffService;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/warehouse-staff/*")
public class WarehouseStaffCreateServlet extends HttpServlet {
    private final WarehouseStaffService svc = new WarehouseStaffService();
    private final Gson gson = new Gson();

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("application/json; charset=UTF-8");
        String path = req.getPathInfo();

        if (path == null || path.equals("/") || path.isEmpty()) {
            // GET /api/warehouse-staff          → Danh sách staff
            List<WarehouseStaffListDTO> staffs = svc.getAllStaff();
            resp.getWriter().write(gson.toJson(staffs));
        } else if (path.startsWith("/detail")) {
            // GET /api/warehouse-staff/detail?id=XX   → Xem chi tiết 1 staff
            try {
                int id = Integer.parseInt(req.getParameter("id"));
                var staff = svc.getStaffById(id);
                resp.getWriter().write(gson.toJson(staff));
            } catch (Exception ex) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\": \"Không tìm thấy nhân viên!\"}");
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("{\"error\": \"API không tồn tại!\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("application/json; charset=UTF-8");
        String path = req.getPathInfo();

        if (path == null || path.equals("/") || path.isEmpty() || path.equals("/create")) {
            // POST /api/warehouse-staff OR /api/warehouse-staff/create   → Thêm staff
            try {
                WarehouseStaffRequestDTO dto = gson.fromJson(req.getReader(), WarehouseStaffRequestDTO.class);
                svc.createStaff(dto);
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.getWriter().write("{\"message\": \"Tạo nhân viên kho thành công!\"}");
            } catch (IllegalArgumentException ex) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\": \"" + ex.getMessage() + "\"}");
            } catch (Exception ex) {
                ex.printStackTrace();
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.getWriter().write("{\"error\": \"" + ex.getMessage() + "\"}");
            }
        } else if (path.startsWith("/update")) {
            // POST /api/warehouse-staff/update?id=XX   → Sửa staff
            try {
                int id = Integer.parseInt(req.getParameter("id"));
                WarehouseStaffRequestDTO dto = gson.fromJson(req.getReader(), WarehouseStaffRequestDTO.class);
                svc.updateStaff(id, dto);
                resp.getWriter().write("{\"message\": \"Cập nhật nhân viên thành công!\"}");
            } catch (IllegalArgumentException ex) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\": \"" + ex.getMessage() + "\"}");
            } catch (Exception ex) {
                ex.printStackTrace();
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.getWriter().write("{\"error\": \"" + ex.getMessage() + "\"}");
            }
        } else if (path.startsWith("/delete")) {
            // POST /api/warehouse-staff/delete?id=XX   → Xóa staff
            try {
                int id = Integer.parseInt(req.getParameter("id"));
                svc.deleteStaff(id);
                resp.getWriter().write("{\"message\": \"Đã xóa nhân viên thành công!\"}");
            } catch (Exception ex) {
                ex.printStackTrace();
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.getWriter().write("{\"error\": \"Lỗi server!\"}");
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("{\"error\": \"API không tồn tại!\"}");
        }
    }

    // Có thể implement PUT và DELETE riêng nếu muốn chuẩn REST hơn (dùng JS fetch với method PUT, DELETE)
}
