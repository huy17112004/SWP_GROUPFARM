// controller/SaveTemplateServlet.java
package controller;

import service.TemplateService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/save_template")
public class SaveTemplateServlet extends HttpServlet {
    private final TemplateService svc = new TemplateService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        req.setCharacterEncoding("UTF-8");
        String name = req.getParameter("name");
        String html = req.getParameter("contentHtml");
        svc.saveOrUpdate(name, html);
        resp.sendRedirect("admin_template.jsp?name=" + name + "&saved=1");
    }
}
