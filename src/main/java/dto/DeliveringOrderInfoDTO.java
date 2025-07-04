package dto;

public class DeliveringOrderInfoDTO {
    private int id;
    private String address;
    private String contactPerson;
    private String phone;
    private String status;

    public DeliveringOrderInfoDTO() {
    }

    public DeliveringOrderInfoDTO(int id, String address, String contactPerson, String phone, String status) {
        this.id = id;
        this.address = address;
        this.contactPerson = contactPerson;
        this.phone = phone;
        this.status = status;
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
}