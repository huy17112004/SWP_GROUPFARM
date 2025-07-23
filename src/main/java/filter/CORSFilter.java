package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebFilter("/*")
public class CORSFilter implements Filter {

    // Nếu muốn, chỉ allow một số origin trắng
    private static final List<String> ALLOWED_ORIGINS = List.of(
            "http://127.0.0.1:5500",
            "http://localhost:5500"
    );

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest  request  = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String origin = request.getHeader("Origin");
        if (origin != null && ALLOWED_ORIGINS.contains(origin)) {
            response.setHeader("Access-Control-Allow-Origin", origin);
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
            // nếu bạn expose thêm header custom nào:
            response.setHeader("Access-Control-Expose-Headers", "redirectUrl");
        }

        // Bắt OPTIONS trả về ngay 200 (preflight)
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        // Cho qua tới servlet tiếp theo
        chain.doFilter(req, res);
    }
}
