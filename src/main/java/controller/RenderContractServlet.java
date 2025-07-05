// controller/RenderContractServlet.java
package controller;

import service.TemplateService;
import entity.ContractTemplate;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.Map;

@WebServlet("/render_contract")
public class RenderContractServlet extends HttpServlet {
    private final TemplateService svc = new TemplateService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String name = req.getParameter("name");
        ContractTemplate tpl = svc.loadTemplate(name);
        String html = tpl != null ? tpl.getContentHtml() : "Template not found";

        // Simple replace placeholder {{key}} bằng value
        Map<String,String> vars = Map.of(
                "companyName",  "Công ty ABC",
                "address",      "123 Đường XYZ"
                // ...
        );
        for (var e : vars.entrySet()) {
            html = html.replace("{{"+e.getKey()+"}}", e.getValue());
        }

        resp.setContentType("text/html; charset=UTF-8");
        resp.getWriter().write(html);
    }
}
