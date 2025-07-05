package dto;

public class StatsDTO {
    private int totalOrders;
    
    public StatsDTO() {
    }
    
    public StatsDTO(int totalOrders) {
        this.totalOrders = totalOrders;
    }
    
    public int getTotalOrders() {
        return totalOrders;
    }
    
    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
    }
}
