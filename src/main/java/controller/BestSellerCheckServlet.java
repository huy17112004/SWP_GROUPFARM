package controller;

import com.google.gson.Gson;
import entity.Seller;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.SellerService;
import util.JpaUtil;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/api/seller/best")
public class BestSellerCheckServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        EntityManager em = JpaUtil.getEntityManager();
        try {
            SellerService sellerService = new SellerService();
            Seller bestSeller = sellerService.selectBestSeller(em);

            if (bestSeller != null) {
                Gson gson = new Gson();
                out.write(gson.toJson(bestSeller.getName()));
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.write("{\"error\": \"No best seller found\"}");
            }
        } catch (RuntimeException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            out.write("{\"error\": \"" + e.getMessage() + "\"}");
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }
}
