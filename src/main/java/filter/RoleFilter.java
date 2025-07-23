package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.*;

@WebFilter("/*")
public class RoleFilter implements Filter {
    // Những prefix chỉ dành cho API hoặc static resources → bypass hoàn toàn
    private static final List<String> SKIP_PATHS = List.of(
            "/api/",
            "/assets/",
            "/css/",
            "/js/",
            "/images/",
            "/email-templete/"
    );

    // Public pages (login, forgot, reset,…)
    private static final List<String> PUBLIC_PATHS = List.of(
            "/login.html",
            "/role-signin",
            "/forgot-password.html",
            "/reset-password.html"
    );

    // Page/folder chỉ cho từng role
    private static final Map<String, List<String>> ROLE_PATHS = new LinkedHashMap<>();
    static {
        ROLE_PATHS.put("ADMIN", List.of(
                "/back-end/view-category.html",            // chỉ folder admin
                "/back-end/view-product.html",
                "/back-end/view-warehouse.html"
        ));
        ROLE_PATHS.put("WAREHOUSE", List.of(
                "/back-end/product.html",        // chỉ folder warehouse
                "/back-end/add-new-product.html"// nếu để ở root
               // nếu vẫn cần
        ));
        ROLE_PATHS.put("SHIPPER", List.of(
                "/back-end/shipper/",
                "/shipper-dashboard.html"
        ));
    }
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest  r = (HttpServletRequest) req;
        HttpServletResponse s = (HttpServletResponse) res;
        String uri = r.getRequestURI();
        String ctx = r.getContextPath();

        // 1) Skip API / static
        for (String p : SKIP_PATHS) {
            if (uri.startsWith(ctx + p)) {
                chain.doFilter(req, res);
                return;
            }
        }

        // 2) Public pages
        for (String p : PUBLIC_PATHS) {
            if (uri.startsWith(ctx + p)) {
                chain.doFilter(req, res);
                return;
            }
        }

        // 3) Session + role
        HttpSession session = r.getSession(false);
        String role = (session != null) ? (String) session.getAttribute("role") : null;
        if (role == null) {
            s.sendRedirect(ctx + "/login.html");
            return;
        }

        // 4) Check allow-list
        List<String> allowed = ROLE_PATHS.getOrDefault(role, Collections.emptyList());
        for (String pattern : allowed) {
            if (uri.startsWith(ctx + pattern)) {
                chain.doFilter(req, res);
                return;
            }
        }

        // 5) Else: redirect or 403
        s.sendRedirect(ctx + "/login.html");
    }
}
