package listener;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebListener;
import util.EmailUtil;
import util.JwtUtil;

@WebListener
public class AppConfigListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();

        JwtUtil.SECRET   = ctx.getInitParameter("JWT_SECRET");
        JwtUtil.EXP_MS   = Long.parseLong(ctx.getInitParameter("JWT_EXP_MS"));
        EmailUtil.SMTP_USER = ctx.getInitParameter("SMTP_EMAIL");
        EmailUtil.SMTP_PASS = ctx.getInitParameter("SMTP_PASSWORD");

        System.out.println("✅ AppConfigListener loaded");
    }
    @Override public void contextDestroyed(ServletContextEvent sce) { }
}
