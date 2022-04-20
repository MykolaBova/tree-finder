package com.tpe.treefinder.concurrent;

import java.util.concurrent.BlockingQueue;

public class WorkerThread implements Runnable {

    String rootPath;

    int depth;

    String mask;

    BlockingQueue<String> messageQueue;

    WorkerThread(String rootPath, int depth, String mask,
            BlockingQueue<String> messageQueue) {
        this.rootPath = rootPath;
        this.depth = depth;
        this.mask = mask;
        this.messageQueue = messageQueue;
    }

    @Override public void run() {
        FileNode root = new FileNode(this.rootPath, messageQueue);
        root.findDeep(this.depth, this.mask);

        try {
            messageQueue.put("bye");
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}
