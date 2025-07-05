package dto;

public class ShippingStatsDTO {
    private int successOrders;
    private int failedOrders;
    private double successRate; // %
    private double failedRate;  // %

    public ShippingStatsDTO(int successOrders, int failedOrders) {
        this.successOrders = successOrders;
        this.failedOrders = failedOrders;
        int total = successOrders + failedOrders;
        this.successRate = total > 0 ? (successOrders * 100.0 / total) : 0;
        this.failedRate = total > 0 ? (failedOrders * 100.0 / total) : 0;
    }

    // Getters and setters
    public int getSuccessOrders() {
        return successOrders; }
    public void setSuccessOrders(int successOrders) {
        this.successOrders = successOrders; }
    public int getFailedOrders() {
        return failedOrders; }
    public void setFailedOrders(int failedOrders) {
        this.failedOrders = failedOrders; }
    public double getSuccessRate() {
        return successRate; }
    public void setSuccessRate(double successRate) {
        this.successRate = successRate; }
    public double getFailedRate() {
        return failedRate; }
    public void setFailedRate(double failedRate) {
        this.failedRate = failedRate; }
}