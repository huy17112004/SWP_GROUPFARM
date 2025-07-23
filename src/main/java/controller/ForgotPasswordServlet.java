package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import service.WholesaleCustomerService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/forgot-password")
public class ForgotPasswordServlet extends HttpServlet {
    private final WholesaleCustomerService svc = new WholesaleCustomerService();
    private final Gson gson = new Gson();

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("application/json;charset=UTF-8");

        try {
            // 1. Đọc email từ body
            JsonObject body = gson.fromJson(req.getReader(), JsonObject.class);
            String email = body.get("email").getAsString();

            // 2. Tự build URL trang reset-password.html
            String resetBaseUrl = req.getScheme()      // http or https
                    + "://" + req.getServerName()         // localhost
                    + ":"   + req.getServerPort()         // :8080
                    + req.getContextPath()                // /YourAppName (nếu có)
                    + "/front-end/reset-password.html";   // path đến file reset

            // 3. Gửi link
            svc.sendPasswordResetLink(email, resetBaseUrl);

            // 4. Trả về thành công
            resp.setStatus(200);
            resp.getWriter().write("{\"message\":\"Link đặt lại mật khẩu đã gửi.\"}");
        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            // In stacktrace ra console và cả vào response để debug
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}