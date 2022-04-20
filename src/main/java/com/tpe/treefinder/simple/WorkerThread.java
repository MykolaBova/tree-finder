package com.tpe.treefinder.simple;

public class WorkerThread implements Runnable {

    String rootPath;

    int depth;

    String mask;

    WorkerThread(String rootPath, int depth, String mask) {
        this.rootPath = rootPath;
        this.depth = depth;
        this.mask = mask;
    }

    @Override public void run() {
        FileNode root = new FileNode(this.rootPath);
        root.findDeep(this.depth, this.mask);
    }
}
