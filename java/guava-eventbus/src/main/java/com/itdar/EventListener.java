package com.itdar;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;
import com.itdar.events.CustomEvent;

public class EventListener {

    public static int eventsHandled;

    @Subscribe
    public void stringEvent(String event) {
        System.out.println(" >> event = " + event);
        eventsHandled++;
    }

    @Subscribe
    public void someCustomEvent(CustomEvent customEvent) {
        System.out.println(" >> customEvent = " + customEvent);
        eventsHandled++;
    }

    @Subscribe
    public void handleDeadEvent(DeadEvent deadEvent) {
        System.out.println(" >> deadEvent = " + deadEvent);
        eventsHandled--;
    }

}
