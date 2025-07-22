package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import dto.DealRequestCreateDTO;
import dto.DealRequestDTO;
//import dto.DealRequestFilterDTO;
import service.DealRequestService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.LocalDateTimeAdapter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@WebServlet("/deal-requests")
public class DealRequestServlet extends HttpServlet {

    private final DealRequestService dealService = new DealRequestService();
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Thiết lập encoding và content type
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");

        // Đọc JSON từ body và chuyển thành DTO inbound
        DealRequestCreateDTO createDTO = gson.fromJson(req.getReader(), DealRequestCreateDTO.class);

        try {
            // Gọi service tạo deal
            DealRequestDTO resultDTO = dealService.createDealRequest(createDTO);

            // Trả về thành công kèm dữ liệu
            MessageResponse response = new MessageResponse("Tạo deal thành công", true, resultDTO);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(gson.toJson(response));

        } catch (IllegalArgumentException ex) {
            // Ví dụ: order item không tồn tại
            MessageResponse response = new MessageResponse(ex.getMessage(), false);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(gson.toJson(response));

        } catch (IllegalStateException ex) {
            // Ví dụ: đã có deal APPROVED
            MessageResponse response = new MessageResponse(ex.getMessage(), false);
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
            resp.getWriter().write(gson.toJson(response));

        } catch (Exception ex) {
            // Lỗi không mong muốn
            ex.printStackTrace();
            MessageResponse response = new MessageResponse("Lỗi máy chủ, vui lòng thử lại sau", false);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(gson.toJson(response));
        }
    }

//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
//            throws ServletException, IOException {
//        // Đọc tất cả query-params, nếu không có thì để null
//        DealRequestFilterDTO f = new DealRequestFilterDTO();
//
//        // status: e.g. ?status=PENDING,APPROVED
//        String statusParam = req.getParameter("status");
//        if (statusParam != null && !statusParam.isBlank()) {
//            List<String> statuses = Arrays.asList(statusParam.split(","));
//            f.setStatuses(statuses);
//        }
//
//        f.setCustomerName(req.getParameter("customerName"));
//
//        String prodId = req.getParameter("productId");
//        if (prodId != null) f.setProductId(Integer.valueOf(prodId));
//
//        f.setProductName(req.getParameter("productName"));
//
//        if (req.getParameter("minTotalOriginalPrice") != null)
//            f.setMinTotalOriginalPrice(new BigDecimal(req.getParameter("minTotalOriginalPrice")));
//        if (req.getParameter("maxTotalOriginalPrice") != null)
//            f.setMaxTotalOriginalPrice(new BigDecimal(req.getParameter("maxTotalOriginalPrice")));
//
//        if (req.getParameter("minTotalProposedPrice") != null)
//            f.setMinTotalProposedPrice(new BigDecimal(req.getParameter("minTotalProposedPrice")));
//        if (req.getParameter("maxTotalProposedPrice") != null)
//            f.setMaxTotalProposedPrice(new BigDecimal(req.getParameter("maxTotalProposedPrice")));
//
//        if (req.getParameter("minDiscountRate") != null)
//            f.setMinDiscountRate(new BigDecimal(req.getParameter("minDiscountRate")));
//        if (req.getParameter("maxDiscountRate") != null)
//            f.setMaxDiscountRate(new BigDecimal(req.getParameter("maxDiscountRate")));
//
//        if (req.getParameter("minQuantity") != null)
//            f.setMinQuantity(Integer.valueOf(req.getParameter("minQuantity")));
//        if (req.getParameter("maxQuantity") != null)
//            f.setMaxQuantity(Integer.valueOf(req.getParameter("maxQuantity")));
//
//        // sortField & sortAsc
//        f.setSortField(req.getParameter("sortField"));
//        String asc = req.getParameter("sortAsc");
//        if (asc != null) f.setSortAsc(Boolean.parseBoolean(asc));
//
//        // Gọi service và trả về JSON
//        List<DealRequestDTO> list = dealService.listDealRequests(f);
//        resp.setContentType("application/json");
//        Type listType = new TypeToken<List<DealRequestDTO>>(){}.getType();
//        resp.getWriter().write(gson.toJson(list, listType));
//    }
}
