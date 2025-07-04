package controller;

import com.google.gson.Gson;
import dto.CustomerAddressDTO;
import dto.CustomerAddressDetailDTO;
import dto.CustomerAddressListDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.CustomerAddressService;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/customer-addresses/*")
public class CustomerAddressServlet extends HttpServlet {

    private final CustomerAddressService customerAddressService = new CustomerAddressService();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("accountId") == null) {
            String json = gson.toJson(new MessageResponse("Unauthorized", false));
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(json);
            return;
        }

        String pathInfo = request.getPathInfo(); // VD: /12
        if (pathInfo != null && pathInfo.length() > 1) {
            try {
                int id = Integer.parseInt(pathInfo.substring(1));
                CustomerAddressDetailDTO dto = customerAddressService.getCustomerAddressDetail(id);
                if (dto == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write(gson.toJson(new MessageResponse("Not Found", false)));
                } else {
                    response.getWriter().write(gson.toJson(dto));
                    response.setStatus(HttpServletResponse.SC_OK);
                }
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write(gson.toJson(new MessageResponse("Bad Request", false)));
            }
            return;
        }

        int accountId = (Integer) session.getAttribute("accountId");
        List<CustomerAddressListDTO> addressList = customerAddressService.getCustomerAddresses(accountId);
        String json = gson.toJson(addressList);
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(json);
    }

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

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Kiểm tra session đăng nhập
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("accountId") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(gson.toJson(new MessageResponse("Unauthorized", false)));
            return;
        }

        // Lấy id địa chỉ từ URL (VD: /api/customer-addresses/12)
        String pathInfo = request.getPathInfo(); // VD: "/12"
        if (pathInfo == null || pathInfo.length() <= 1) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(gson.toJson(new MessageResponse("Missing address ID", false)));
            return;
        }

        int addressId;
        try {
            addressId = Integer.parseInt(pathInfo.substring(1));
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(gson.toJson(new MessageResponse("Invalid address ID", false)));
            return;
        }

        // Đọc body
        CustomerAddressDetailDTO dto;
        try (BufferedReader reader = request.getReader()) {
            dto = gson.fromJson(reader, CustomerAddressDetailDTO.class);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(gson.toJson(new MessageResponse("Invalid request body", false)));
            return;
        }

        int accountId = (Integer) session.getAttribute("accountId");
        boolean ok = customerAddressService.updateCustomerAddress(
                accountId,
                addressId,
                dto.getStreet(),
                dto.getWardID(),
                dto.getLatitude(),
                dto.getLongitude()
        );

        if (ok) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(gson.toJson(new MessageResponse("Update address successfully!", true)));
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(gson.toJson(new MessageResponse("Update address failed!", false)));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("accountId") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(gson.toJson(new MessageResponse("Unauthorized", false)));
            return;
        }

        // Lấy id địa chỉ từ URL (VD: /api/customer-addresses/12)
        String pathInfo = request.getPathInfo(); // VD: "/12"
        if (pathInfo == null || pathInfo.length() <= 1) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(gson.toJson(new MessageResponse("Missing address ID", false)));
            return;
        }

        int addressId;
        try {
            addressId = Integer.parseInt(pathInfo.substring(1));
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(gson.toJson(new MessageResponse("Invalid address ID", false)));
            return;
        }

        int accountId = (Integer) session.getAttribute("accountId");
        boolean ok = customerAddressService.deleteCustomerAddress(accountId, addressId);

        if (ok) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(gson.toJson(new MessageResponse("Delete address successfully!", true)));
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(gson.toJson(new MessageResponse("Delete address failed!", false)));
        }
    }


}
