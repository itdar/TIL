package com.itdar;

import com.google.common.eventbus.AsyncEventBus;
import com.itdar.events.CustomEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GuavaEventBusApp {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("### Starting Event Bus ###");

        // async
        ExecutorService executor = Executors.newFixedThreadPool(5);
        AsyncEventBus asyncEventBus = new AsyncEventBus("async", executor);

//        // sync
//        EventBus eventBus = new EventBus("sync");
//
//        EventListener listener = new EventListener();
//        eventBus.register(listener);

        // cancel describe
//        eventBus.unregister(listener);

        asyncEventBus.register(new EventListener());
//        eventBus.register(new MessageListener());

//        asyncEventBus.post("ASYNC event"); // eventBus.post를 먼저 실행하면 블로킹됨.
//        eventBus.post("SYNC event");

//        System.out.println("before stringEvent: " + eventsHandled);
//        eventBus.post("String Event");
//        System.out.println("after stringEvent: " + eventsHandled + "\n");

//        CustomEvent customEvent = new CustomEvent("Custom Event Action", "thread1", false);

//        System.out.println("before customEvent: " + eventsHandled);
//        eventBus.post(customEvent);
//        System.out.println("after customEvent: " + eventsHandled + "\n");

        CustomEvent customEvent1 = new CustomEvent("Custom Event 111 Action", "thread1", false);
//        System.out.println("before customEvent1: " + eventsHandled);
        asyncEventBus.post(customEvent1);
//        System.out.println("after customEvent1: " + eventsHandled + "\n");

        CustomEvent customEvent2 = new CustomEvent("Custom Event 222 Action", "thread1", true);
//        System.out.println("before customEvent2: " + eventsHandled);
        asyncEventBus.post(customEvent2);
//        System.out.println("after customEvent2: " + eventsHandled + "\n");

//        System.out.println("before undefinedEvent: " + eventsHandled);
//        eventBus.post(1);
//        System.out.println("after undefinedEvent: " + eventsHandled + "\n");

        if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
            executor.shutdown();
        }

    }

}
