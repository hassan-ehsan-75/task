package com.hassan.lalamove.events;

public class EventMessage {

    public final int message;
    public final int status;

    public EventMessage(int message, int status) {
        this.message = message;
        this.status = status;
    }
}
