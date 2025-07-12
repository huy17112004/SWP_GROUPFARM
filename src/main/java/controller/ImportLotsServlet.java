package controller;

import com.google.gson.Gson;
import dto.LotImportRequestDTO;
import service.InventoryService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

@WebServlet("/api/import-lots")
public class ImportLotsServlet extends HttpServlet {
    private final InventoryService service = new InventoryService();
    private final Gson gson = new Gson();

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        resp.setHeader("Access-Control-Allow-Origin", "*");
//        resp.setContentType("application/json;charset=UTF-8");
//
//        LotImportRequestDTO dto = gson.fromJson(req.getReader(), LotImportRequestDTO.class);
//        HttpSession session = req.getSession(false);
//        if (session == null || session.getAttribute("staffId") == null) {
//            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            resp.getWriter().write("{\"error\":\"Unauthorized\"}");
//            return;
//        }
//        int staffId = (Integer) session.getAttribute("staffId");
//
//        try {
//            service.importLots(dto, staffId);
//            resp.setStatus(HttpServletResponse.SC_CREATED);
//            resp.getWriter().write("{\"status\":\"success\"}");
//        } catch (RuntimeException ex) {
//            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            resp.getWriter().write(gson.toJson(Map.of("error", ex.getMessage())));
//        }
//    }
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    // CORS
    resp.setHeader("Access-Control-Allow-Origin", "*");
    resp.setContentType("application/json;charset=UTF-8");

    // Lấy hoặc khởi tạo session
    HttpSession session = req.getSession(true);

    // 1–5 là các accountId của Admin; nếu chưa có trong session thì dùng 3
    Integer accountId = (Integer) session.getAttribute("accountId");
    if (accountId == null) {
        accountId = 6;                       // ← chọn đại Admin id = 3
        session.setAttribute("accountId", 6);
    }

    // Đọc DTO
    LotImportRequestDTO dto = gson.fromJson(req.getReader(), LotImportRequestDTO.class);

    try {
        service.importLots(dto, accountId);
        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.getWriter().write(gson.toJson(Map.of("status","success")));
    } catch (Exception ex) {
        ex.printStackTrace();
        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        resp.getWriter().write(gson.toJson(Map.of(
                "error", ex.getClass().getSimpleName(),
                "message", ex.getMessage(),
                "stack",   sw.toString()
        )));
    }
}

}