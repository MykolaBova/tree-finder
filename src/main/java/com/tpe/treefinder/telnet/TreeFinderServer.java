package com.tpe.treefinder.telnet;

import static com.tpe.treefinder.telnet.PrinterThread.MESSAGE_QUEUE_BOUND;
import static com.tpe.treefinder.telnet.WorkerThread.TASK_QUEUE_BOUND;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TreeFinderServer {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    static BlockingQueue<Task> workerQueue = new LinkedBlockingQueue<>(TASK_QUEUE_BOUND);
    static BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>(MESSAGE_QUEUE_BOUND);

    static int taskCounter = 0;

    public void start(int serverPort, String rootPath) {
        try {
            serverSocket = new ServerSocket(serverPort);

            while(true) {

                if(workerQueue.remainingCapacity() <= 1) {
                    Thread.sleep(1000);
                    break;
                }

                clientSocket = serverSocket.accept();
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                out.println("Please enter the depth");
                int depth = Integer.parseInt(in.readLine());
                System.out.println("depth = " + depth);
                if(depth == -1) {
                    break;
                }

                out.println("Please enter the mask");
                String mask = in.readLine();
                System.out.println("mask = " + mask);

                Task task = new Task();
                task.setClientSocket(clientSocket);
                task.setDepth(depth);
                task.setIn(in);
                task.setOut(out);
                task.setRootPath(rootPath);
                task.setInitial(true);
                task.setTaskId(taskCounter);
                task.setMask(mask);
                task.setWorkerQueue(workerQueue);
                taskCounter++;

                try {
                    workerQueue.put(task);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            stop();
        }

    }

    public void stop() {
        try {
            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        String rootPath;
        int serverPort;

        if(args.length != 2) {
            help();
        }
        rootPath = args[0];
        serverPort = -1;
        try {
            serverPort = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            help();
        }
        System.out.println("rootPath = " + rootPath);
        System.out.println("serverPort = " + serverPort);

        Thread workerThread = new Thread(new WorkerThread(workerQueue, messageQueue));
        workerThread.start();

        Thread printerThread = new Thread(new PrinterThread(messageQueue));
        printerThread.start();

        TreeFinderServer server = new TreeFinderServer();
        server.start(serverPort, rootPath);
    }

    private static void help() {
        System.out.println("Expected two command line arguments");
        System.out.println("1 rootPath String");
        System.out.println("2 port int (-1 to stop the server)");
    }
}
