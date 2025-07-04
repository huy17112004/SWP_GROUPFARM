package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.CompletedOrderInfoDTO;
import dto.DeliveringOrderInfoDTO;
import dto.PendingOrderInfoDTO;
import service.WholesaleOrderService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import entity.Account;

@WebServlet("/shipper/orders/*")
public class ShipperOrderServlet extends HttpServlet {
    private WholesaleOrderService orderService;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        orderService = new WholesaleOrderService();
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            pathInfo = "/";
        }

        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            switch (pathInfo) {
                case "/pending":
                    getPendingOrders(request, response, out);
                    break;
                case "/delivering":
                    getDeliveringOrders(request, response, out);
                    break;
                case "/completed":
                    getCompletedOrders(request, response, out);
                    break;
                default:
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print(gson.toJson(new ErrorResponse("Endpoint không tồn tại")));
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(gson.toJson(new ErrorResponse("Lỗi server: " + e.getMessage())));
        }
    }

    private void getPendingOrders(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
        try {
            List<PendingOrderInfoDTO> pendingOrders = orderService.getPendingOrderBasicInfo();

            ApiResponse<List<PendingOrderInfoDTO>> apiResponse = new ApiResponse<>();
            apiResponse.setSuccess(true);
            apiResponse.setMessage("Lấy danh sách đơn hàng chờ xử lý thành công");
            apiResponse.setData(pendingOrders);

            out.print(gson.toJson(apiResponse));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(gson.toJson(new ErrorResponse("Lỗi khi lấy danh sách đơn hàng chờ xử lý: " + e.getMessage())));
        }
    }

    private void getDeliveringOrders(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
        try {
            List<DeliveringOrderInfoDTO> deliveringOrders = orderService.getDeliveringOrderBasicInfo();

            ApiResponse<List<DeliveringOrderInfoDTO>> apiResponse = new ApiResponse<>();
            apiResponse.setSuccess(true);
            apiResponse.setMessage("Lấy danh sách đơn hàng đang giao thành công");
            apiResponse.setData(deliveringOrders);

            out.print(gson.toJson(apiResponse));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(gson.toJson(new ErrorResponse("Lỗi khi lấy danh sách đơn hàng đang giao: " + e.getMessage())));
        }
    }

    private void getCompletedOrders(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
        try {
            List<CompletedOrderInfoDTO> completedOrders = orderService.getCompletedOrderBasicInfo();

            ApiResponse<List<CompletedOrderInfoDTO>> apiResponse = new ApiResponse<>();
            apiResponse.setSuccess(true);
            apiResponse.setMessage("Lấy danh sách đơn hàng đã hoàn thành thành công");
            apiResponse.setData(completedOrders);

            out.print(gson.toJson(apiResponse));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(
                    gson.toJson(new ErrorResponse("Lỗi khi lấy danh sách đơn hàng đã hoàn thành: " + e.getMessage())));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            pathInfo = "/";
        }

        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            switch (pathInfo) {
                case "/update-status":
                    updateOrderStatus(request, response, out);
                    break;
                default:
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print(gson.toJson(new ErrorResponse("Endpoint không tồn tại")));
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(gson.toJson(new ErrorResponse("Lỗi server: " + e.getMessage())));
            e.printStackTrace();
        }
    }

    private void updateOrderStatus(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
        // TODO: Implement method to update order status
        response.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
        out.print(gson.toJson(new ErrorResponse("Chức năng đang được phát triển")));
    }

    // Inner classes for API response
    public static class ApiResponse<T> {
        private boolean success;
        private String message;
        private T data;

        public ApiResponse() {
        }

        public ApiResponse(boolean success, String message, T data) {
            this.success = success;
            this.message = message;
            this.data = data;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }
    }

    public static class ErrorResponse {
        private boolean success = false;
        private String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}