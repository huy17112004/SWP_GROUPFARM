package controller;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import dto.SignInRequestDTO;
import entity.WholesaleCustomer;
import service.WholesaleCustomerService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/signin")
public class SigninCustomerServlet extends HttpServlet {
    private final WholesaleCustomerService svc = new WholesaleCustomerService();
    private final Gson gson = new Gson();

    // CORS preflight
    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String origin = req.getHeader("Origin");
        resp.setHeader("Access-Control-Allow-Origin", origin);
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    // Xử lý POST /signin
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 1) CORS headers
        String origin = req.getHeader("Origin");
        resp.setHeader("Access-Control-Allow-Origin", origin);
        resp.setHeader("Access-Control-Allow-Credentials", "true");

        resp.setContentType("application/json; charset=UTF-8");

        try {
            SignInRequestDTO dto = gson.fromJson(req.getReader(), SignInRequestDTO.class);

            WholesaleCustomer user = svc.login(dto);
            // 2) Lưu session
            HttpSession session = req.getSession(true);
            session.setAttribute("accountId", user.getId());
            session.setAttribute("accountType", "CUSTOMER");
            session.setAttribute("username", user.getUsername());
            session.setAttribute("name", user.getCompanyName());

            // 3) Nếu rememberMe thì set cookie
            if (dto.isRememberMe()) {
                Cookie cookie = new Cookie("JSESSIONID", session.getId());
                cookie.setHttpOnly(true);
                cookie.setMaxAge(7 * 24 * 60 * 60); // 7 ngày
                cookie.setPath(req.getContextPath().isEmpty() ? "/" : req.getContextPath());
                resp.addCookie(cookie);
            }

            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("{\"message\":\"Đăng nhập thành công\"}");
        } catch (JsonSyntaxException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"JSON không hợp lệ\"}");
        } catch (IllegalStateException e) {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"Server lỗi, thử lại sau.\"}");
        }
    }
}
