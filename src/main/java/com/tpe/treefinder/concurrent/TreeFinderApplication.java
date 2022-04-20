package com.tpe.treefinder.concurrent;

import static com.tpe.treefinder.concurrent.PrinterThread.MESSAGE_QUEUE_BOUND;

import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TreeFinderApplication {

    public static void main(String[] args) throws InterruptedException {

        BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>(MESSAGE_QUEUE_BOUND);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter root rootPath: ");
        String rootPath = scanner.nextLine();
        System.out.println("Please enter depth: ");
        int depth = scanner.nextInt();

        // workaround
        //https://stackoverflow.com/questions/13102045/scanner-is-skipping-nextline-after-using-next-or-nextfoo
        scanner.nextLine();

        System.out.println("Please enter mask: ");
        String mask = scanner.nextLine();

        Thread workerThread = new Thread(new WorkerThread(rootPath, depth, mask,
                messageQueue));
        workerThread.start();

        Thread printerThread = new Thread(new PrinterThread(messageQueue));
        printerThread.start();
    }
}
