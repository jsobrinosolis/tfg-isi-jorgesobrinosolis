package entities;

import jade.core.AID;

public class Car {
    private String brand;
    private String model;
    private int currentPrice;
    private AID standingBidBuyer;
    private String status;

    public Car (String brand, String model, int startingPrice){
        this.brand = brand;
        this.model = model;
        this.currentPrice = startingPrice;
        this.status = "new";
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(int currentPrice) {
        this.currentPrice = currentPrice;
    }

    public AID getStandingBidBuyer() {
        return standingBidBuyer;
    }

    public void setStandingBidBuyer(AID standingBidBuyer) {
        this.standingBidBuyer = standingBidBuyer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
