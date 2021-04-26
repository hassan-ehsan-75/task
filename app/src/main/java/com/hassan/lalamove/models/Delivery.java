package com.hassan.lalamove.models;


import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "deliveries")
public class Delivery implements Serializable {
    @PrimaryKey()
    @NonNull
    private String id;
    private String remarks;
    private String pickupTime;
    private String goodsPicture;
    private String deliveryFee;
    private String surcharge;
    private Route route;
    private Sender sender;
    private int is_favorite=0;

    public Delivery(@NonNull String id, String remarks, String pickupTime, String goodsPicture, String deliveryFee, String surcharge, Route route, Sender sender, int is_favorite) {
        this.id = id;
        this.remarks = remarks;
        this.pickupTime = pickupTime;
        this.goodsPicture = goodsPicture;
        this.deliveryFee = deliveryFee;
        this.surcharge = surcharge;
        this.route = route;
        this.sender = sender;
        this.is_favorite = is_favorite;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }

    public String getGoodsPicture() {
        return goodsPicture;
    }

    public void setGoodsPicture(String goodsPicture) {
        this.goodsPicture = goodsPicture;
    }

    public String getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(String deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public String getSurcharge() {
        return surcharge;
    }

    public void setSurcharge(String surcharge) {
        this.surcharge = surcharge;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public int getIs_favorite() {
        return is_favorite;
    }

    public void setIs_favorite(int is_favorite) {
        this.is_favorite = is_favorite;
    }
}
