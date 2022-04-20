package com.tpe.treefinder.telnet;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Deque;
import java.util.concurrent.BlockingQueue;

public class Task {

    private Socket clientSocket;

    private PrintWriter out;

    private BufferedReader in;

    int depth;

    String mask;

    String rootPath;

    boolean isInitial;

    Deque<INode> stack;

    int taskId;

    BlockingQueue<Task> workerQueue;

    public PrintWriter getOut() {
        return out;
    }

    public void setOut(PrintWriter out) {
        this.out = out;
    }

    public BufferedReader getIn() {
        return in;
    }

    public void setIn(BufferedReader in) {
        this.in = in;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public String getMask() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public boolean isInitial() {
        return isInitial;
    }

    public void setInitial(boolean initial) {
        isInitial = initial;
    }

    public Deque<INode> getStack() {
        return stack;
    }

    public void setStack(Deque<INode> stack) {
        this.stack = stack;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public BlockingQueue<Task> getWorkerQueue() {
        return workerQueue;
    }

    public void setWorkerQueue(BlockingQueue<Task> workerQueue) {
        this.workerQueue = workerQueue;
    }
}
