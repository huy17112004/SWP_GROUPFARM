package controller;

import com.google.gson.Gson;
import dto.InventoryResponseDTO;
import service.StockLotService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/warehouses/*/inventory")
public class WarehouseInventoryServlet extends HttpServlet {
    private final StockLotService service = new StockLotService();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getPathInfo(); // expecting "/{warehouseId}/inventory"
        String[] parts = path.split("/");
        int warehouseId = Integer.parseInt(parts[1]);
        List<InventoryResponseDTO> inv = service.getInventoryByWarehouse(warehouseId);
        resp.setContentType("application/json;charset=UTF-8");
        resp.getWriter().write(gson.toJson(inv));
    }
}
