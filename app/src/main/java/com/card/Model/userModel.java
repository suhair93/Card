package com.card.Model;

public class userModel {
    private  String UID;
    private  Double price;
    private  String ID_number_u;
    private  String ID_Card;

    public userModel() {
    }

    public userModel(String UID, Double price, String ID_number_u, String ID_Card) {
        this.UID = UID;
        this.price = price;
        this.ID_number_u = ID_number_u;
        this.ID_Card = ID_Card;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getID_number() {
        return ID_number_u;
    }

    public void setID_number(String ID_number) {
        this.ID_number_u = ID_number;
    }

    public String getID_Card() {
        return ID_Card;
    }

    public void setID_Card(String ID_Card) {
        this.ID_Card = ID_Card;
    }
}
