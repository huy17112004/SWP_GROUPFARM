package controller;

public class MessageResponse {
    private boolean success;
    private String message;
    private Object data; // Có thể null, hoặc trả về kèm dữ liệu bổ sung (vd: orderId, user info...)

    public MessageResponse() {}

    public MessageResponse(String message, boolean success) {
        this.success = success;
        this.message = message;
    }

    public MessageResponse(String message, boolean success, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    // Getter, setter
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Object getData() { return data; }
    public void setData(Object data) { this.data = data; }
}
