package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dto.ProvinceExternalDTO;
import service.LocationService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@WebServlet("/api/import-location")
public class ImportLocationServlet extends HttpServlet {

    private final LocationService locationService = new LocationService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String apiUrl = "https://provinces.open-api.vn/api/?depth=3";
        String json = fetchExternalJson(apiUrl);

        // Parse json thành List<ProvinceExternalDTO>
        Gson gson = new Gson();
        Type listType = new TypeToken<List<ProvinceExternalDTO>>(){}.getType();
        List<ProvinceExternalDTO> provinces = gson.fromJson(json, listType);

        // Gọi service import vào DB
        locationService.importFullLocation(provinces);

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"message\":\"Imported provinces, districts, wards successfully!\"}");
    }

    private String fetchExternalJson(String urlString) throws IOException {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        try (BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"))) {
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
        }
        return result.toString();
    }
}
