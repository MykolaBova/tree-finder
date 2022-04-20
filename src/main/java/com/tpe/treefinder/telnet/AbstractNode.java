package com.tpe.treefinder.telnet;

import java.io.IOException;
import java.util.Deque;
import java.util.LinkedList;

public abstract class AbstractNode implements INode {

    static final long TASK_MAX_TIME = 1000;

    protected int depth;

    @Override
    public final boolean containsMask(String mask) {
        return getName().contains(mask);
    }

    @Override
    public final void findDeep(Task currentTask) {
        String mask = currentTask.getMask();
        int depth = currentTask.getDepth();
        Deque<INode> stack = currentTask.getStack();
        long start = System.currentTimeMillis();

        while (!stack.isEmpty()) {

            // Check processing time and save stack
            long end = System.currentTimeMillis();
            long elapsedTime = end - start;
            if(elapsedTime >= TASK_MAX_TIME) {
                currentTask.setStack(stack);

                try {
                    currentTask.getWorkerQueue().put(currentTask);
                    break;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }

            INode node = stack.pop();

            if(node.containsMask(mask) && node.getDepth() <= depth) {
                node.printFullPath(currentTask);
            }

            for(INode child : node.getChildren()) {
                if(child.getDepth() <= depth) {
                    stack.push(child);
                } else {
                    break;
                }
            }
        }

        if(stack.isEmpty()) {
            try {
                currentTask.getClientSocket().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getDepth() {
        return this.depth;
    }
}
