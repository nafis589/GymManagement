package models;

public class Subscription {
    private int id;
    private String offerName;
    private int durationMonths;
    private double monthlyPrice;

    // Constructor
    public Subscription(int id, String offerName, int durationMonths, double monthlyPrice) {
        this.id = id;
        this.offerName = offerName;
        this.durationMonths = durationMonths;
        this.monthlyPrice = monthlyPrice;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getOfferName() { return offerName; }
    public void setOfferName(String offerName) { this.offerName = offerName; }
    
    public int getDurationMonths() { return durationMonths; }
    public void setDurationMonths(int durationMonths) { this.durationMonths = durationMonths; }
    
    public double getMonthlyPrice() { return monthlyPrice; }
    public void setMonthlyPrice(double monthlyPrice) { this.monthlyPrice = monthlyPrice; }
}