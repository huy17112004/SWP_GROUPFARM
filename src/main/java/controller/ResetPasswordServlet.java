package controller;

import com.google.gson.Gson;
import dto.ResetPasswordRequestDTO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.WholesaleCustomerService;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/api/reset-password")
public class ResetPasswordServlet extends HttpServlet {
    private final WholesaleCustomerService customerService = new WholesaleCustomerService();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        ResetPasswordRequestDTO req = gson.fromJson(reader, ResetPasswordRequestDTO.class);

        try {
            customerService.resetPassword(req.getEmail(), req.getOtp(), req.getNewPassword());

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(gson.toJson(new MessageResponse("Password reset successfully", true)));
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(gson.toJson(new MessageResponse(e.getMessage(), false)));
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

