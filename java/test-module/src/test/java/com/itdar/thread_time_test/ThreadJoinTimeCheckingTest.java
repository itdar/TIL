package com.itdar.thread_time_test;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ThreadJoinTimeCheckingTest {

    @DisplayName("쓰레드 start() 후에 join을 했을 때와 안했을 경우의 시간 차이를 본다.")
    @Test
    public void Thread_join_여부에_따른_속도_차이_테스트() throws InterruptedException {
        TestThread1 testThread1;
        TestThread2 testThread2;

        long startTime = System.nanoTime();

        for (int i = 0; i < 100; ++i) {
            testThread1 = new TestThread1(i);
            testThread2 = new TestThread2(i);

            testThread1.start();
            testThread1.join();
            testThread2.start();
            testThread2.join();
        }

        long elapsedTime = System.nanoTime() - startTime;

        System.out.println("Join을 넣었을 경우 ==========================");
        System.out.println("elapsedTime to reach here = " + elapsedTime);
        System.out.println("sec: " + elapsedTime / 1000 / 1000 / 1000);
        System.out.println("milli sec: " + elapsedTime / 1000 / 1000);
        System.out.println("micro sec: " + elapsedTime / 1000);
        System.out.println("nano sec: " + elapsedTime);
        System.out.println("==========================");

        startTime = System.nanoTime();

        for (int i = 0; i < 100; ++i) {
            testThread1 = new TestThread1(i);
            testThread2 = new TestThread2(i);

            testThread1.start();
            testThread2.start();
        }

        elapsedTime = System.nanoTime() - startTime;

        System.out.println("Join을 넣지 않았을 경우 ==========================");
        System.out.println("elapsedTime to reach here = " + elapsedTime);
        System.out.println("sec: " + elapsedTime / 1000 / 1000 / 1000);
        System.out.println("milli sec: " + elapsedTime / 1000 / 1000);
        System.out.println("micro sec: " + elapsedTime / 1000);
        System.out.println("nano sec: " + elapsedTime);
        System.out.println("==========================");
    }
}
