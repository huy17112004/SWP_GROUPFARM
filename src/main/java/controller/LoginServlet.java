package controller;

import com.google.gson.Gson;
import dto.LoginRequestDTO;
import dto.LoginResponseDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import service.AccountService;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/api/login")
public class LoginServlet extends HttpServlet {
    private final AccountService accountService = new AccountService();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        HttpSession session = request.getSession();

        try (BufferedReader reader = request.getReader()) {
            LoginRequestDTO loginRequest = gson.fromJson(reader, LoginRequestDTO.class);
            LoginResponseDTO loginResponse = accountService.login(loginRequest.getUsername(),
                    loginRequest.getPassword());

            if (loginResponse.isSuccess()) {
                // Lưu thông tin vào session
                session.setAttribute("accountId", loginResponse.getAccountId());
                session.setAttribute("accountType", loginResponse.getAccountType());
                session.setAttribute("username", loginResponse.getUsername());
                session.setAttribute("name", loginResponse.getName());

                // Trả về response thành công
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                // Trả về response thất bại
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }

            // Gửi response về client
            response.getWriter().write(gson.toJson(loginResponse));
        }
    }
}