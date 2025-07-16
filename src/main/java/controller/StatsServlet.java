package controller;

import com.google.gson.Gson;
import dto.StatsDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.StatsService;

import java.io.IOException;

@WebServlet("/api/stats")
public class StatsServlet extends HttpServlet {
     private  final StatsService statsService = new StatsService();
     private final Gson gson = new Gson();


     @Override
     protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
          HttpSession session  = req.getSession(false);
          if(session == null || session.getAttribute("accountId") == null){
               resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Admin not logged in");
               return;
          }
          try {
               StatsDTO dto = statsService.getOrdersToday();
               resp.setContentType("application/json;charset=UTF-8");
               resp.getWriter().write(gson.toJson(dto));

          } catch (Exception e){
               resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
               resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
          }
     }
}
