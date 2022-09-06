package com.itdar;

import com.google.common.eventbus.Subscribe;
import java.util.concurrent.TimeUnit;

public class MessageListener {

    @Subscribe
    public void receive(String message) throws InterruptedException {
        System.out.println("Receiving message... " + message);
        receiving(3, message);
        System.out.println("Done");
    }

    private static void receiving(int count, String message) throws InterruptedException {
        for (int i = 0; i < count; i++) {
            System.out.println("... {}" + message);
            TimeUnit.SECONDS.sleep(1L);
        }
    }

}
