package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.DealConfirmDTO;
import dto.DealRequestDTO;
import service.DealRequestService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.LocalDateTimeAdapter;

import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/deal-requests/approve")
public class DealRequestApproveServlet extends HttpServlet {
    private final DealRequestService service = new DealRequestService();
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 1. Đọc JSON body thành DealRejectDTO
        DealConfirmDTO approveDto = gson.fromJson(req.getReader(), DealConfirmDTO.class);

        // 2. Gọi service để xử lý
        DealRequestDTO updated = service.approveDeal(approveDto);

        // 3. Trả về client JSON của bản ghi đã được cập nhật
        resp.setContentType("application/json");
        resp.getWriter().write(gson.toJson(updated));
    }
}
