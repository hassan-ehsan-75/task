package com.hassan.lalamove.models;

import java.io.Serializable;

import androidx.room.Entity;

@Entity
public class Route implements Serializable {
    private String start;
    private String end;

    public Route(String start, String end) {
        this.start = start;
        this.end = end;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
