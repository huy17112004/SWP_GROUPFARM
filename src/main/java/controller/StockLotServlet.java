package controller;

import com.google.gson.Gson;
import dto.StockLotRequestDTO;
import dto.StockLotResponseDTO;
import service.StockLotService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/api/stocklots/*")
public class StockLotServlet extends HttpServlet {
    private final StockLotService service = new StockLotService();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=UTF-8");
        String path = req.getPathInfo();
        if(path!=null && path.length()>1) {
            int id = Integer.parseInt(path.substring(1));
            StockLotResponseDTO dto = service.getStockLotById(id);
            if(dto==null) { resp.setStatus(404); return; }
            resp.getWriter().write(gson.toJson(dto));
        } else {
            List<StockLotResponseDTO> list = service.getAllStockLots();
            resp.getWriter().write(gson.toJson(list));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        StockLotRequestDTO r = gson.fromJson(req.getReader(), StockLotRequestDTO.class);
        StockLotResponseDTO created = service.createStockLot(r);
        resp.setStatus(201);
        resp.getWriter().write(gson.toJson(created));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getPathInfo().substring(1));
        StockLotRequestDTO r = gson.fromJson(req.getReader(), StockLotRequestDTO.class);
        StockLotResponseDTO updated = service.updateStockLot(id, r);
        if(updated==null) { resp.setStatus(404); return; }
        resp.getWriter().write(gson.toJson(updated));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getPathInfo().substring(1));
        boolean ok = service.deleteStockLot(id);
        resp.setStatus(ok?204:404);
    }
}