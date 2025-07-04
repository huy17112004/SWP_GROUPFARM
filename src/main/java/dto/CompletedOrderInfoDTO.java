package dto;

import java.time.LocalDateTime;

public class CompletedOrderInfoDTO {
    private int id;
    private String address;
    private String contactPerson;
    private String phone;
    private String status; // Trạng thái cuối cùng (COMPLETED, RETURNED, CANCELLED)
    private LocalDateTime completedAt; // Ngày giờ hoàn thành
    private String note; // Lý do thất bại hoặc ghi chú khác

    public CompletedOrderInfoDTO() {
    }

    public CompletedOrderInfoDTO(int id, String address, String contactPerson, String phone, String status,
            LocalDateTime completedAt, String note) {
        this.id = id;
        this.address = address;
        this.contactPerson = contactPerson;
        this.phone = phone;
        this.status = status;
        this.completedAt = completedAt;
        this.note = note;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}