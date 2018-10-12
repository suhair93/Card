package com.card.Model;


public class PurchaseModel {
    public String ID;
    public String Price;
    public String Date;

    public PurchaseModel(String price, String date) {
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
