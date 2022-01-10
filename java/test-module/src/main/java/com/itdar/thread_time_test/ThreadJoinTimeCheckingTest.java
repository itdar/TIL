package com.itdar.thread_time_test;

public class ThreadJoinTimeCheckingTest {

    public static void main(String[] args) throws Exception {
//        TestThread1 testThread3;
//        TestThread2 testThread4;
//
//        long startTime = System.nanoTime();
//
//        for (int i = 0; i < 500; ++i) {
//            testThread3 = new TestThread1(i);
//            testThread4 = new TestThread2(i);
//
//            // join을 했을 때와 안했을 경우의 시간 차이를 본다.
//            testThread3.start();
//            testThread3.join();
//            testThread4.start();
//            testThread4.join();
//        }
//
//        long elapsedTime = System.nanoTime() - startTime;
//
//        System.out.println("elapsedTime to reach here = " + elapsedTime);
//        System.out.println("sec: " + elapsedTime / 1000 / 1000 / 1000);
//        System.out.println("milli sec: " + elapsedTime / 1000 / 1000);
//        System.out.println("micro sec: " + elapsedTime / 1000);
//        System.out.println("nano sec: " + elapsedTime);
    }
}

class TestThread1 extends Thread {
    private int index;

    public TestThread1(int index) {
        this.index = index;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(index + " ");
    }
}

class TestThread2 extends Thread {
    private int index;

    public TestThread2(int index) {
        this.index = index;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(index + " ");
    }
}
