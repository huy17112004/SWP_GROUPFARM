package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.ChatMessageDTO;
import dto.MessageRequestDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import service.MessageService;
import util.LocalDateTimeAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/messages")
public class MessageServlet extends HttpServlet {
    private final MessageService messageService = new MessageService();
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try {
            HttpSession session = req.getSession(false);
            Integer accountId = (session != null) ? (Integer) session.getAttribute("accountId") : null;
            if (accountId == null) {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                gson.toJson(new MessageResponse("Bạn chưa đăng nhập!", false), resp.getWriter());
                return;
            }

            BufferedReader reader = req.getReader();
            String json = reader.lines().collect(Collectors.joining());
            MessageRequestDTO requestDTO = gson.fromJson(json, MessageRequestDTO.class);

            ChatMessageDTO savedMessage = messageService.saveMessage(accountId, requestDTO);

            resp.setStatus(HttpServletResponse.SC_CREATED);
            gson.toJson(new MessageResponse("Lưu thành công!", true, savedMessage), resp.getWriter());
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            gson.toJson(new MessageResponse("Lưu thất bại!", false), resp.getWriter());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession(false);
        Integer accountId = (session != null) ? (Integer) session.getAttribute("accountId") : null;
        if (accountId == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            gson.toJson(new MessageResponse("Bạn chưa đăng nhập!", false), resp.getWriter());
            return;
        }

        String orderIdParam = req.getParameter("orderId");
        String sinceParam = req.getParameter("since");

        if (orderIdParam == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            gson.toJson(new MessageResponse("Không có order!", false), resp.getWriter());
            return;
        }

        try {
            int orderId = Integer.parseInt(orderIdParam);
            List<ChatMessageDTO> messages;
            if (sinceParam != null && !sinceParam.isEmpty()) {
                try {
                    LocalDateTime since = LocalDateTime.parse(sinceParam);
                    messages = messageService.getMessagesByOrderIdSince(accountId, orderId, since);
                } catch (DateTimeParseException ex) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    gson.toJson(new MessageResponse("Tham số 'since' không đúng định dạng thời gian ISO!", false), resp.getWriter());
                    return;
                }
            } else {
                messages = messageService.getMessagesByOrderId(accountId, orderId);
            }
            resp.setStatus(HttpServletResponse.SC_OK);
            gson.toJson( messages, resp.getWriter());

        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid orderId parameter");
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Failed to fetch messages.");
        }
    }


}
