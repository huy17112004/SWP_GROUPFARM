package controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import dao.DistrictDAO;
import entity.District;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import service.LocationService;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@WebServlet("/api/geo/district")
public class DistrictGeoServlet extends HttpServlet {

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int districtId = Integer.parseInt(req.getParameter("districtId"));

        LocationService locationService = new LocationService();

        try {
            String encoded = URLEncoder.encode(locationService.getAddressForAPI(districtId), StandardCharsets.UTF_8);
            String lat = null;
            String lon = null;
            String url = "https://nominatim.openstreetmap.org/search?format=json&q=" + encoded;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("User-Agent", "JavaGeoApp")
                    .build();

            HttpResponse<String> apiResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            JsonArray array = JsonParser.parseString(apiResponse.body()).getAsJsonArray();

            resp.setContentType("application/json;charset=UTF-8");


            if (array.size() > 0) {
                JsonObject obj = array.get(0).getAsJsonObject();
                lat = obj.get("lat").getAsString();
                lon = obj.get("lon").getAsString();
            } else {
                String provinceOnly = locationService.getOnlyProvinceForAPI(districtId);
                String encodedProvince = URLEncoder.encode(provinceOnly, StandardCharsets.UTF_8);
                String urlProvince = "https://nominatim.openstreetmap.org/search?format=json&q=" + encodedProvince;

                HttpRequest request2 = HttpRequest.newBuilder()
                        .uri(URI.create(urlProvince))
                        .header("User-Agent", "JavaGeoApp")
                        .build();

                HttpResponse<String> response2 = httpClient.send(request2, HttpResponse.BodyHandlers.ofString());
                JsonArray array2 = JsonParser.parseString(response2.body()).getAsJsonArray();

                if (array2.size() > 0) {
                    JsonObject obj = array2.get(0).getAsJsonObject();
                    lat = obj.get("lat").getAsString();
                    lon = obj.get("lon").getAsString();
                } else {
                    lat = "not_found";
                    lon = "not_found";
                }
            }

            JsonObject result = new JsonObject();
            result.addProperty("lat", lat);
            result.addProperty("lon", lon);

            resp.getWriter().write(gson.toJson(result));

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.setContentType("application/json;charset=UTF-8");
            resp.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}
