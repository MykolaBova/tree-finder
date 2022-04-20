package com.tpe.treefinder.telnet;

import java.util.concurrent.BlockingQueue;

public class PrinterThread implements Runnable {

    final static int MESSAGE_QUEUE_BOUND = 10;

    BlockingQueue<String> messageQueue;

    PrinterThread(BlockingQueue<String> messageQueue) {
        this.messageQueue = messageQueue;
    }

    @Override
    public void run() {
        while (true) {
            String message = null;
            try {
                message = messageQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }

            System.out.println(message);

            if(message != null && message.equals("bye")) {
                break;
            }
        }
    }
}
