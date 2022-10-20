package com.itdar;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import com.itdar.events.CustomEvent;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class EventListener {

//    public static int eventsHandled;

//    @Subscribe
//    public void stringEvent(String event) {
//        System.out.println(" >> event = " + event);
//        eventsHandled++;
//    }

    @AllowConcurrentEvents
    @Subscribe
    public void someCustomEvent(CustomEvent customEvent) throws InterruptedException {
        System.out.println(" >> customEvent = " + customEvent);

        System.out.println("test start");
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        for (Thread thread : threadSet) {
            System.out.println("Name: " + thread.getName() + " || ID: " + thread.getId() + " || State: " + thread.getState());
        }
        System.out.println("test end");

        if (customEvent.isKill()) {
//            Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
            for (Thread thread : threadSet) {
                if (thread.getName().equals(customEvent.getThreadName())) {
                    for (int i = 1; i <= 3; ++i) {
                        System.out.println(" ========================= " + i + "초 후 종료: " + customEvent.getThreadName());
                        TimeUnit.SECONDS.sleep(1L);
                    }
                    thread.interrupt();
                    return;
                }
            }
        }

        Thread.currentThread().setName(customEvent.getThreadName());

        for (int i = 1; i <= 7; ++i) {
            TimeUnit.SECONDS.sleep(1L);
            System.out.println(" ============ " + Thread.currentThread().getName() + " 이름의 thread " + i + "초간 실행 중");
        }
    }

//    @Subscribe
//    public void handleDeadEvent(DeadEvent deadEvent) {
//        System.out.println(" >> deadEvent = " + deadEvent);
//        eventsHandled--;
//    }

}
