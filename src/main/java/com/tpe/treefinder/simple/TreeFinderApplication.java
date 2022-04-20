package com.tpe.treefinder.simple;

import java.util.Scanner;

public class TreeFinderApplication {
    public static void main(String[] args) {
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

        Thread workerThread = new Thread(new WorkerThread(rootPath, depth, mask));
        workerThread.start();
    }
}
