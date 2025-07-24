package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.WarehouseStaffDAO;
import dto.StockTransferRequestDTO;
import entity.WarehouseManager;
import entity.WarehouseStaff;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.StockTransferService;
import service.WarehouseManagerService;
import service.WarehouseStaffService;
import util.LocalDateTimeAdapter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/stock-transfers/source-warehouse")
public class TransferSourceWarehouseServlet extends HttpServlet {

    private final StockTransferService stockTransferService = new StockTransferService();
    private final WarehouseStaffService warehouseStaffService = new WarehouseStaffService();
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");

        HttpSession session = req.getSession(false);
        Integer accountId = (session != null) ? (Integer) session.getAttribute("accountId") : null;
        String accountType = (session != null) ? (String) session.getAttribute("accountType") : null;

        if (accountId == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write(gson.toJson(new MessageResponse("Bạn chưa đăng nhập!", false)));
            return;
        }

        if (!"WAREHOUSE".equals(accountType)) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write(gson.toJson(new MessageResponse("Bạn không phải nhân viên kho!", false)));
            return;
        }

        WarehouseStaff warehouseStaff = warehouseStaffService.getWarehouseStaffById(accountId);

        String statusParam = req.getParameter("status");
        List<String> statusList = null;
        if (statusParam != null && !statusParam.isBlank()) {
            statusList = List.of(statusParam.split("\\s*,\\s*")); // Tách theo dấu phẩy, bỏ khoảng trắng
        }

        List<StockTransferRequestDTO> dtos =
                stockTransferService.findBySourceWarehouseAndStatuses(warehouseStaff.getWarehouse().getId(), statusList);

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(gson.toJson(dtos));
    }
}
