// src/main/java/controller/RoleSigninServlet.java
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
        // (Bạn có thể kiểm nếu origin nằm trong whitelist thì mới cho qua)
        resp.setHeader("Access-Control-Allow-Origin", origin);
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        // Cho phép front-end đọc trường redirectUrl:
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
            RoleSignInRequestDTO dto = gson.fromJson(req.getReader(), RoleSignInRequestDTO.class);
            Account acc = authService.loginWithRole(
                    dto.getUsername(),
                    dto.getPassword(),
                    dto.getRole()
            );

            // Lưu session
            HttpSession session = req.getSession(true);
            session.setAttribute("currentAccount", acc);
            String role = dto.getRole().toUpperCase();
            session.setAttribute("role", role);

            // Xác định URL redirect tùy role
            String ctx = req.getContextPath();
            String redirectUrl;
            switch (role) {
                case "ADMIN":
                    redirectUrl = ctx + "/back-end/view-category.html";
                    break;
                case "WAREHOUSE":
                    redirectUrl = ctx + "/back-end/add-new-stoklot.html";
                    break;
                case "SHIPPER":
                    redirectUrl = ctx + "/shipper/dashboard.html";
                    break;
                default:
                    redirectUrl = ctx + "/";
            }

            // Trả về JSON kèm redirectUrl
            Map<String,String> result = new HashMap<>();
            result.put("message", "Đăng nhập thành công");
            result.put("role", role);
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
