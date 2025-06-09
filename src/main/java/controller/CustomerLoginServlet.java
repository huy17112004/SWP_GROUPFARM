package controller;

import com.google.gson.Gson;
import dao.WholesaleCustomerDAO;
import dto.LoginRequest;
import entity.WholesaleCustomer;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import service.WholesaleCustomerService;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/api/login/customer")
public class CustomerLoginServlet extends HttpServlet {

    private final WholesaleCustomerService wholesaleCustomerService = new WholesaleCustomerService();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Đọc JSON request body
        BufferedReader reader = request.getReader();
        LoginRequest login = gson.fromJson(reader, LoginRequest.class);

        // Kiểm tra đăng nhập
        WholesaleCustomer customer = wholesaleCustomerService.login(login.getUsername(), login.getPassword());

        if (customer != null) {
            // Trả JSON thành công
            String json = gson.toJson(new MessageResponse("Login successfully", true));
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(json);
        } else {
            // Trả JSON thất bại
            String json = gson.toJson(new MessageResponse("Login failed", false));
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(json);
        }
    }

    class MessageResponse {
        private String message;
        private boolean success;

        public MessageResponse(String message, boolean success) {
            this.message = message;
            this.success = success;
        }

        public String getMessage() { return message; }
        public boolean isSuccess() { return success; }
    }
}
