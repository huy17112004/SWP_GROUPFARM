package controller;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import dto.RegisterRequestDTO;
import service.WholesaleCustomerService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/signup")
public class SignupCustomerServlet extends HttpServlet {
    private final WholesaleCustomerService svc = new WholesaleCustomerService();
    private final Gson gson = new Gson();

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET ,POST, DELETE, PUT, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setStatus(HttpServletResponse.SC_OK);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("application/json; charset=UTF-8");

        try {
            // 1. Kiểm header
            String ct = req.getContentType();
            if (ct == null || !ct.contains("application/json")) {
                resp.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
                resp.getWriter().write("{\"error\":\"Chỉ hỗ trợ application/json\"}");
                return;
            }

            // 2. Đọc và parse JSON
            RegisterRequestDTO dto = gson.fromJson(req.getReader(), RegisterRequestDTO.class);

            // 3. Gọi service
            svc.signup(dto);

            // 4. Trả success
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("{\"message\":\"Đăng ký thành công! Vui lòng kiểm tra email.\"}");

        } catch (JsonSyntaxException e) {
            // JSON không hợp lệ
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"JSON không hợp lệ\"}");

        } catch (IllegalArgumentException e) {
            // validate lỗi (username/email exist, pw mismatch,…)
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");

        } catch (Exception e) {
            // mọi lỗi khác
            e.printStackTrace(); // in log
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"Server lỗi, thử lại sau.\"}");
        }
    }
}


