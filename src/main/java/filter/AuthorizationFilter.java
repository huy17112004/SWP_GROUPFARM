//package filter;
//
//import entity.*;
//import jakarta.servlet.*;
//import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.http.*;
//
//import util.RoleMapping;
//
//import java.io.IOException;
//
//@WebFilter("/api/*")
//public class AuthorizationFilter implements Filter {
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//
//        HttpServletRequest req = (HttpServletRequest) request;
//        HttpServletResponse res = (HttpServletResponse) response;
//        HttpSession session = req.getSession(false);
//        String path = req.getRequestURI();
//
//        if (path.equals("/api/signup") || path.equals("/api/login")) {
//            chain.doFilter(request, response); // ✅ Không kiểm tra session
//            return;
//        }
//
//        Account user = (session != null) ? (Account) session.getAttribute("user") : null;
//
//        if (user == null) {
//            res.sendError(401, "Bạn chưa đăng nhập.");
//            return;
//        }
//
//        // Phân quyền theo vai trò chính
//        if (path.startsWith("/api/admin/system") && !RoleMapping.isAdminSystem(user)) {
//            res.sendError(403, "Chỉ admin hệ thống được truy cập.");
//            return;
//        }
//
//        if (path.startsWith("/api/admin/business") && !RoleMapping.isAdminBusiness(user)) {
//            res.sendError(403, "Chỉ admin kinh doanh được truy cập.");
//            return;
//        }
//
//        if (path.startsWith("/api/seller") && !(user instanceof Seller)) {
//            res.sendError(403, "Chỉ seller được phép.");
//            return;
//        }
//
//        if (path.startsWith("/api/customer") && !(user instanceof WholesaleCustomer)) {
//            res.sendError(403, "Chỉ khách hàng được phép.");
//            return;
//        }
//
//        if (path.startsWith("/api/shipper") && !(user instanceof Shipper)) {
//            res.sendError(403, "Chỉ Shipper được phép.");
//            return;
//        }
//
//
//        if (path.startsWith("/api/manager") && !(user instanceof Manager)) {
//            res.sendError(403, "Chỉ Manager được phép.");
//            return;
//        }
//
//
//        if (path.startsWith("/api/warehouse/manager") && !(user instanceof WarehouseManager)) {
//            res.sendError(403, "Chỉ Quản lý kho được phép.");
//            return;
//        }
//
//
//        if (path.startsWith("/api/warehouse/staff") && !(user instanceof WarehouseStaff)) {
//            res.sendError(403, "Chỉ Nhân viên kho được phép.");
//            return;
//        }
//
//
//        chain.doFilter(request, response);
//    }
//}
