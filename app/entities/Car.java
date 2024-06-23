package entities;

import jade.core.AID;

public class Car {

    private int id;
    private String brand;
    private String model;
    private int currentPrice;
    private AID standingBidBuyer;
    private int sellerID;

    public Car (String brand, String model, int startingPrice, int sellerID){
        this.brand = brand;
        this.model = model;
        this.currentPrice = startingPrice;
        this.sellerID = sellerID;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

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

    public int getSellerID() {
        return sellerID;
    }

    public void setSellerID(int sellerID) {
        this.sellerID = sellerID;
    }
}
