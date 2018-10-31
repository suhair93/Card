package com.card.Model;


public class PurchaseModel {
    private String ID;
    private String Price;
    private String Date;


    public PurchaseModel() {
    }

    public PurchaseModel(String price, String date) {
        Price = price;
        Date = date;
    }

    public PurchaseModel(String ID, String price, String date) {
        this.ID = ID;
        Price = price;
        Date = date;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
