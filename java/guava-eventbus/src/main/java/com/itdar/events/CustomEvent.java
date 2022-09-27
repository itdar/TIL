package com.itdar.events;

public class CustomEvent {
    private String action;
    private String threadName;
    private boolean kill;

    public CustomEvent(String action, String threadName, boolean kill) {
        this.action = action;
        this.threadName = threadName;
        this.kill = kill;
    }

    @Override
    public String toString() {
        return "CustomEvent{" +
            "action='" + action + '\'' +
            ", threadName='" + threadName + '\'' +
            ", kill=" + kill +
            '}';
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public boolean isKill() {
        return kill;
    }

    public void setKill(boolean kill) {
        this.kill = kill;
    }
}
