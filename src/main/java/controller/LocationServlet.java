package controller;

import com.google.gson.Gson;
import dto.ForgotPasswordRequestDTO;
import dto.LocationDTO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.LocationService;
import service.WholesaleCustomerService;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/provinces", "/districts", "/wards"})
public class LocationServlet extends HttpServlet {
    private final WholesaleCustomerService customerService = new WholesaleCustomerService();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");

        String servletPath = request.getServletPath();
        LocationService locationService = new LocationService();

        if (servletPath.equals("/provinces")) {
            List<LocationDTO> provinces = locationService.getProvinces();
            String json = gson.toJson(provinces);
            response.getWriter().write(json);
        } else if (servletPath.equals("/districts")) {
            int provinceId = Integer.parseInt(request.getParameter("provinceId"));
            List<LocationDTO> districts = locationService.getDistricts(provinceId);
            String json = gson.toJson(districts);
            response.getWriter().write(json);
        } else if (servletPath.equals("/wards")) {
            int districtId = Integer.parseInt(request.getParameter("districtId"));
            List<LocationDTO> wards = locationService.getWards(districtId);
            String json = gson.toJson(wards);
            response.getWriter().write(json);
        }
    }
}

