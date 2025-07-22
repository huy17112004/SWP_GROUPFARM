package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.DealRequestFilterDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.DealRequestService;
import util.LocalDateTimeAdapter;

import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet(name = "ManagerDealRequestServlet", urlPatterns = "/api/manager/deal-requests")
public class ManagerDealRequestServlet extends HttpServlet {
    private final DealRequestService dealRequestService = new DealRequestService();
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            DealRequestFilterDTO filter = new DealRequestFilterDTO();
            if (req.getParameter("status") != null) {
                filter.setStatus(req.getParameter("status"));
            }
            if (req.getParameter("page") != null) {
                filter.setPage(Integer.parseInt(req.getParameter("page")));
            }
            if (req.getParameter("limit") != null) {
                filter.setLimit(Integer.parseInt(req.getParameter("limit")));
            }

            String jsonResponse = gson.toJson(dealRequestService.listDealRequestsForManager(filter));
            resp.getWriter().write(jsonResponse);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(gson.toJson("Error: " + e.getMessage()));
        }
    }
} 