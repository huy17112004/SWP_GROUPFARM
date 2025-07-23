package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.WholesaleCustomerService;

import java.io.IOException;


@WebServlet("/verify")
public class VerifyServlet extends HttpServlet {
    private final WholesaleCustomerService svc = new WholesaleCustomerService();

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET ,POST, DELETE, PUT, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setStatus(HttpServletResponse.SC_OK);}

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        String token = req.getParameter("token");
        boolean ok = svc.verify(token);

        if (ok) {
            // chuyển qua trang success
            req.getRequestDispatcher("/verify-success.jsp")
                    .forward(req, resp);
        } else {
            // chuyển qua trang failure
            req.getRequestDispatcher("/verify-failure.jsp")
                    .forward(req, resp);
        }
    }
}
