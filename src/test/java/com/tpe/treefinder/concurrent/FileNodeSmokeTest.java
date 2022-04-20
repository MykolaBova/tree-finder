package com.tpe.treefinder.concurrent;

import static com.tpe.treefinder.concurrent.PrinterThread.MESSAGE_QUEUE_BOUND;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileNodeSmokeTest {

    private static final String userDir = System.getProperty("user.dir");;
    private static final String testDataPath = userDir + "/TreeFinderTestData1";

    @BeforeAll
    public static void init() throws IOException {
        new File(testDataPath + "/AA").mkdirs();
        new File(testDataPath + "/AA/AB").mkdirs();
        new File(testDataPath + "/AA/BB").mkdirs();
        new File(testDataPath + "/file1.txt").createNewFile();
        new File(testDataPath + "/AA/AB.txt").createNewFile();
    }

    @AfterAll
    public static void clean() {
        File testDir = new File(testDataPath);
        deleteRecursively(testDir);
    }

    private static void deleteRecursively(File dir) {
        for (File f : dir.listFiles()) {
            if (f.isDirectory()) {
                deleteRecursively(f);
            }
            f.delete();
        }
        dir.delete();
    }

    @Test
    void testFindDeep_Basic_depth0() {
        BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>(MESSAGE_QUEUE_BOUND);
        Thread printerThread = new Thread(new PrinterThread(messageQueue));
        printerThread.start();

        FileNode root = new FileNode(testDataPath + "/AA", messageQueue);
        root.findDeep(0, "A");
    }

    @Test
    void testFindDeep_Basic_depth1() {
        BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>(MESSAGE_QUEUE_BOUND);
        Thread printerThread = new Thread(new PrinterThread(messageQueue));
        printerThread.start();

        FileNode root = new FileNode(testDataPath + "/AA", messageQueue);
        root.findDeep( 1, "A");
    }

    @Test
    void testFindDeep_EmptyDir() {
        BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>(MESSAGE_QUEUE_BOUND);
        Thread printerThread = new Thread(new PrinterThread(messageQueue));
        printerThread.start();

        FileNode root = new FileNode(testDataPath + "/AA/BB", messageQueue);
        root.findDeep( 0, "A");
    }

    @Test
    void testFindDeep_NoDir() {
        BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>(MESSAGE_QUEUE_BOUND);
        Thread printerThread = new Thread(new PrinterThread(messageQueue));
        printerThread.start();

        FileNode root = new FileNode(testDataPath + "/AA1", messageQueue);
        root.findDeep( 0, "A");
    }

    @Test
    void testFindDeep_File() {
        BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>(MESSAGE_QUEUE_BOUND);
        Thread printerThread = new Thread(new PrinterThread(messageQueue));
        printerThread.start();

        FileNode root = new FileNode(testDataPath + "/file1.txt", messageQueue);
        root.findDeep( 0, "f");
    }

    @Test
    void testFindDeep_NegativeDepth() {
        BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>(MESSAGE_QUEUE_BOUND);
        Thread printerThread = new Thread(new PrinterThread(messageQueue));
        printerThread.start();

        FileNode root = new FileNode(testDataPath + "/file1.txt", messageQueue);
        root.findDeep( -1, "f");
    }
}
