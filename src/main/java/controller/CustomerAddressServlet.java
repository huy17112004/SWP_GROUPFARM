package controller;

import com.google.gson.Gson;
import dto.CustomerAddressDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.CustomerAddressService;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/api/customer-addresses")
public class CustomerAddressServlet extends HttpServlet {

    private final CustomerAddressService customerAddressService = new CustomerAddressService();
    private final Gson gson = new Gson();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try (BufferedReader reader = request.getReader()) {
            CustomerAddressDTO customerAddress = gson.fromJson(reader, CustomerAddressDTO.class);

            HttpSession session = request.getSession(false);
            int accountId = (Integer) session.getAttribute("accountId");

            if (session == null || session.getAttribute("accountId") == null) {
                String json = gson.toJson(new MessageResponse("Unauthorized", false));
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write(json);
                return;
            }

            boolean ok = customerAddressService.addCustomerAddress(accountId,customerAddress.getStreet(),customerAddress.getWardID(),customerAddress.getLatitude(),customerAddress.getLongitude());
            if (ok) {
                String json = gson.toJson(new MessageResponse("Add customer address successfully!", true));
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write(json);
            } else {
                String json = gson.toJson(new MessageResponse("Add customer address failed!", false));
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write(json);
            }

        }
    }
}
