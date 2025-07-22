package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.StockLotAllocationDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import service.StockLotService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/stock-lot/allocation")
public class StockLotAllocationServlet extends HttpServlet {

    private final StockLotService stockLotService = new StockLotService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String orderIdParam = req.getParameter("orderId");

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter out = resp.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            int orderId = Integer.parseInt(orderIdParam);
            List<StockLotAllocationDTO> dtos = stockLotService.getAllocationByOrderId(orderId);

            String json = objectMapper.writeValueAsString(dtos);
            out.print(json);
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\": \"Invalid orderId\"}");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\": \"" + e.getMessage() + "\"}");
        } finally {
            out.flush();
        }
    }
}
