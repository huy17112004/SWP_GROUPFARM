package controller;

import com.google.gson.Gson;
import dto.WarehouseStaffRequestDTO;
import service.WarehouseStaffService;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/api/warehouse-staff/create")
public class WarehouseStaffCreateServlet extends HttpServlet {
    private final WarehouseStaffService svc = new WarehouseStaffService();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("application/json; charset=UTF-8");
        try {
            // Đọc dữ liệu JSON từ body
            WarehouseStaffRequestDTO dto = gson.fromJson(req.getReader(), WarehouseStaffRequestDTO.class);

            // Xử lý tạo nhân viên
            svc.createStaff(dto);

            // Thành công
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write("{\"message\": \"Tạo nhân viên kho thành công!\"}");
        } catch (IllegalArgumentException ex) {
            // Lỗi validate
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"" + ex.getMessage() + "\"}");
        } catch (Exception ex) {
            resp.getWriter().write("{\"error\": \"" + ex.toString() + "\"}");

            // Trả về thông báo lỗi thật sự, có cả stacktrace nếu muốn
            ex.printStackTrace(); // In lỗi ra console/log server (để dev dễ nhìn)
            resp.getWriter().write("{\"error\": \"" + ex.getMessage() + "\"}");
        }
    }
}
