package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import service.WholesaleCustomerService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/reset-password")
public class ResetPasswordServlet extends HttpServlet {
    private final WholesaleCustomerService svc = new WholesaleCustomerService();
    private final Gson gson = new Gson();

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("application/json;charset=UTF-8");

        JsonObject body = gson.fromJson(req.getReader(), JsonObject.class);
        String token = body.get("token").getAsString();
        String newPassword = body.get("newPassword").getAsString();
        String confirm = body.get("confirmPassword").getAsString();

        try {
            if (!newPassword.equals(confirm)) {
                throw new IllegalArgumentException("Xác nhận mật khẩu không khớp");
            }
            // có thể tái dùng validateSyntax để check độ mạnh mật khẩu
            svc.resetPasswordByToken(token, newPassword);
            resp.getWriter().write("{\"message\":\"Đặt lại mật khẩu thành công.\"}");
            resp.setStatus(200);
        } catch (IllegalArgumentException e) {
            resp.setStatus(400);
            resp.getWriter().write("{\"error\":\""+e.getMessage()+"\"}");
        } catch (Exception e) {
            resp.setStatus(500);
            resp.getWriter().write("{\"error\":\"Server lỗi, thử lại sau.\"}");
        }
    }
}