package dto;

import java.math.BigDecimal;
import java.util.List;

public class RevenueDTO {
    private BigDecimal todayRevenue;
    private BigDecimal weekRevenue;
    private BigDecimal monthRevenue;
    private BigDecimal yearRevenue;
    private String currency;
    private String period;

    private List<String> labels;
    private List<BigDecimal> values;

    public RevenueDTO() {
        this.currency = "VND";
    }
    
    public RevenueDTO(BigDecimal todayRevenue, BigDecimal weekRevenue, BigDecimal monthRevenue, BigDecimal yearRevenue) {
        this.todayRevenue = todayRevenue;
        this.weekRevenue = weekRevenue;
        this.monthRevenue = monthRevenue;
        this.yearRevenue = yearRevenue;
        this.currency = "VND";
    }
    
    // Getters and Setters
    public BigDecimal getTodayRevenue() {
        return todayRevenue;
    }
    
    public void setTodayRevenue(BigDecimal todayRevenue) {
        this.todayRevenue = todayRevenue;
    }
    
    public BigDecimal getWeekRevenue() {
        return weekRevenue;
    }
    
    public void setWeekRevenue(BigDecimal weekRevenue) {
        this.weekRevenue = weekRevenue;
    }
    
    public BigDecimal getMonthRevenue() {
        return monthRevenue;
    }
    
    public void setMonthRevenue(BigDecimal monthRevenue) {
        this.monthRevenue = monthRevenue;
    }
    
    public BigDecimal getYearRevenue() {
        return yearRevenue;
    }
    
    public void setYearRevenue(BigDecimal yearRevenue) {
        this.yearRevenue = yearRevenue;
    }
    
    public String getCurrency() {
        return currency;
    }
    
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    
    public String getPeriod() {
        return period;
    }
    
    public void setPeriod(String period) {
        this.period = period;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public List<BigDecimal> getValues() {
        return values;
    }

    public void setValues(List<BigDecimal> values) {
        this.values = values;
    }
}
