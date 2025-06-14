package controller;

import com.google.gson.Gson;
import dto.SignUpRequestDTO;
import entity.WholesaleCustomer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.WholesaleCustomerService;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/api/signup/customer")
public class CustomerSignUpServlet extends HttpServlet {

    private final WholesaleCustomerService wholesaleCustomerService = new WholesaleCustomerService();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Đọc JSON request body
        BufferedReader reader = request.getReader();
        SignUpRequestDTO signup = gson.fromJson(reader, SignUpRequestDTO.class);

        // Kiểm tra và xử lý đăng ký
        try {
            if (signup == null) {
                throw new IllegalArgumentException("Request data is missing");
            }

            WholesaleCustomer customer = wholesaleCustomerService.signup(signup.getUsername(), signup.getEmail(), signup.getPassword());
            if (customer != null) {
                // Trả JSON thành công
                String json = gson.toJson(new MessageResponse("Account created successfully", true));
                response.setStatus(HttpServletResponse.SC_CREATED);
                response.getWriter().write(json);
            } else {
                // Trả JSON thất bại
                String json = gson.toJson(new MessageResponse("Signup failed", false));
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write(json);
            }
        } catch (IllegalArgumentException e) {


            // Xử lý lỗi xác thực
            String json = gson.toJson(new MessageResponse(e.getMessage(), false));
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(json);
        } catch (Exception e) {


            // Xử lý lỗi server
            String json = gson.toJson(new MessageResponse("Failed to create account: " + e.getMessage(), false));
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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