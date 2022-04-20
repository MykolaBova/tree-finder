package com.tpe.treefinder.telnet;

import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;

public class WorkerThread implements Runnable {

//    final static int TASK_QUEUE_BOUND = 10;
    final static int TASK_QUEUE_BOUND = 4;

    BlockingQueue<String> messageQueue;

    BlockingQueue<Task> taskQueue;

    WorkerThread(BlockingQueue<Task> taskQueue,
            BlockingQueue<String> messageQueue) {

        this.messageQueue = messageQueue;
        this.taskQueue = taskQueue;
    }

    @Override public void run() {

        while(true) {

            // Get Task from workerQueue
            Task currentTask = null;
            try {
                currentTask = taskQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
            if(currentTask == null || currentTask.getDepth() == -1) {
                break;
            }

            // Iterate file tree
            if(currentTask.isInitial()) {
                FileNode root = new FileNode(currentTask.rootPath, messageQueue);
                Deque<INode> stack = new LinkedList<>();
                stack.push(root);
                currentTask.setStack(stack);

                root.findDeep(currentTask);
            } else {

                FileNode root = (FileNode) currentTask.getStack().peek();
                if (root != null) {
                    root.findDeep(currentTask);
                }
            }
        }

        try {
            messageQueue.put("bye");
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}
