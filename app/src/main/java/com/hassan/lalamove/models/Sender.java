package com.hassan.lalamove.models;

import java.io.Serializable;

import androidx.room.Entity;

@Entity
public class Sender implements Serializable {
    private String phone;
    private String name;
    private String email;

    public Sender(String phone, String name, String email) {
        this.phone = phone;
        this.name = name;
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
