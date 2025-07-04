package dto;

public class PendingOrderInfoDTO {
    private int id;
    private String address;
    private String contactPerson;
    private String phone;
    private String note;

    public PendingOrderInfoDTO() {
    }

    public PendingOrderInfoDTO(int id, String address, String contactPerson, String phone, String note) {
        this.id = id;
        this.address = address;
        this.contactPerson = contactPerson;
        this.phone = phone;
        this.note = note;
    }

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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}