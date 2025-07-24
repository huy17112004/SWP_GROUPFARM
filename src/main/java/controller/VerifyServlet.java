package controller;

import dto.WarehouseStaffRequestDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.WarehouseDetailViewService;
import service.WarehouseStaffService;
import service.WholesaleCustomerService;

import java.io.IOException;


@WebServlet("/verify")
public class VerifyServlet extends HttpServlet {
    private final WholesaleCustomerService svc = new WholesaleCustomerService();

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET ,POST, DELETE, PUT, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setStatus(HttpServletResponse.SC_OK);}

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        String token = req.getParameter("token");
        boolean ok = svc.verify(token);

        if (ok) {
            // chuyển qua trang success
            req.getRequestDispatcher("/verify-success.jsp")
                    .forward(req, resp);
        } else {
            // chuyển qua trang failure
            req.getRequestDispatcher("/verify-failure.jsp")
                    .forward(req, resp);
        }
    }

    @WebServlet("/warehouse-staff/create")
    public static class WarehouseStaffCreateServlet extends HttpServlet {
        private final WarehouseStaffService svc = new WarehouseStaffService();
        private final WarehouseDetailViewService warehouseService = new WarehouseDetailViewService();

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            // load danh sách kho để select
            req.setAttribute("warehouses", warehouseService.getAllDetailView()); // bạn có thể thêm method lấy list Warehouse
            req.getRequestDispatcher("/WEB-INF/views/createWarehouseStaff.jsp")
                    .forward(req, resp);
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            // đọc param
            WarehouseStaffRequestDTO dto = new WarehouseStaffRequestDTO();
            dto.setUsername(req.getParameter("username"));
            dto.setRawPassword(req.getParameter("password"));
            dto.setName(req.getParameter("name"));
            dto.setEmail(req.getParameter("email"));
            dto.setPhone(req.getParameter("phone"));
            dto.setWarehouseId(Integer.parseInt(req.getParameter("warehouseId")));

            // gọi service
            svc.createStaff(dto);

            // chuyển hướng về danh sách hoặc trang thành công
            resp.sendRedirect(req.getContextPath() + "/warehouse-staff/list");
        }
    }
}
