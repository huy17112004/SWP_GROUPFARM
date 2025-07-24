package controller;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import dto.RoleSignInRequestDTO;
import entity.Account;
import service.RoleAuthService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/role-signin")
public class RoleSigninServlet extends HttpServlet {
    private final RoleAuthService authService = new RoleAuthService();
    private final Gson gson = new Gson();

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String origin = req.getHeader("Origin");
        resp.setHeader("Access-Control-Allow-Origin", origin);
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setHeader("Access-Control-Expose-Headers", "Content-Type");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String origin = req.getHeader("Origin");
        resp.setHeader("Access-Control-Allow-Origin", origin);
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setContentType("application/json; charset=UTF-8");

        try {
            // 1. Parse DTO
            RoleSignInRequestDTO dto = gson.fromJson(req.getReader(), RoleSignInRequestDTO.class);

            // 2. Thực hiện login
            Account acc = authService.loginWithRole(
                    dto.getUsername(),
                    dto.getPassword(),
                    dto.getRole()
            );

            // 3. Lưu session với nhiều attribute
            HttpSession session = req.getSession(true);
            session.setAttribute("currentAccount", acc);

            String role = dto.getRole().toUpperCase();
            session.setAttribute("role", role);

            // Thêm các thông tin chi tiết hơn
            session.setAttribute("userId", acc.getId());
            session.setAttribute("accountId", acc.getId());
            session.setAttribute("accountType",  role);
            session.setAttribute("username",     acc.getUsername());

            // 4. Xác định URL redirect theo role
            String ctx = req.getContextPath();
            String redirectUrl;
            switch (role) {
                case "SELLER":
                    redirectUrl = ctx + "/back-end/order-list-seller.html";
                    break;
                case "MANAGER":
                    redirectUrl = ctx + "/back-end/reports.html";
                    break;
                case "WAREHOUSE":
                    redirectUrl = ctx + "/back-end/warehouse-stocktransfer-management.html";
                    break;
                case "SHIPPER":
                    redirectUrl = ctx + "/back-end/shipper-order-confirmation.html";
                    break;
                default:
                    redirectUrl = ctx + "/";
            }

            // 5. Trả về JSON kèm redirectUrl
            Map<String,String> result = new HashMap<>();
            result.put("message",     "Đăng nhập thành công");
            result.put("role",        role);
            result.put("redirectUrl", redirectUrl);

            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(gson.toJson(result));

        } catch (JsonSyntaxException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"JSON không hợp lệ\"}");
        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        } catch (IllegalStateException e) {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"Server lỗi, thử lại sau.\"}");
        }
    }
}
