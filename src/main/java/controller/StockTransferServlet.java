package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.StockTransferRequestDTO;
import dto.StockTransferResponseDTO;
import service.StockTransferService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.LocalDateTimeAdapter;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/stock-transfers-create")
public class StockTransferServlet extends HttpServlet {
    private final StockTransferService stockTransferService = new StockTransferService();
    private final Gson gson =  new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        try {
            String orderIdParam = req.getParameter("orderId");
            int orderId = Integer.parseInt(orderIdParam);
            List<StockTransferResponseDTO> result = stockTransferService.startStockTransferForOrder(orderId);
            MessageResponse response = new MessageResponse("Stock transfer started", true, result);
            out.print(gson.toJson(response));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            MessageResponse response = new MessageResponse(e.getMessage(), false, null);
            out.print(gson.toJson(response));
        } finally {
            out.flush();
        }
    }


} 