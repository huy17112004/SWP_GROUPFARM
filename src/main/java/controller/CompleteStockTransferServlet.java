package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.StockTransferResponseDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import service.StockTransferService;
import util.LocalDateTimeAdapter;

import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/stock-transfers/complete")
public class CompleteStockTransferServlet extends HttpServlet {

    private final StockTransferService stockTransferService = new StockTransferService();
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");

        String idParam = req.getParameter("id");
        if (idParam == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(gson.toJson(new MessageResponse("Thiếu tham số ID!", false)));
            return;
        }

        try {
            int stockTransferId = Integer.parseInt(idParam);
            StockTransferResponseDTO dto = stockTransferService.completeStockTransfer(stockTransferId);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(gson.toJson(dto));
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(gson.toJson(new MessageResponse("ID không hợp lệ!", false)));
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(gson.toJson(new MessageResponse("Lỗi xử lý complete!", false)));
        }
    }
}
