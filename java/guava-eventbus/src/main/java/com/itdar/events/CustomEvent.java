package com.itdar.events;

public class CustomEvent {
    private String action;

    public CustomEvent(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

}
