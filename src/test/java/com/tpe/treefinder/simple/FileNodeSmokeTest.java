package com.tpe.treefinder.simple;

import java.io.File;
import java.io.IOException;

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
    void testFindDeep_Basic() {
        FileNode root = new FileNode(testDataPath + "/AA");

        root.findDeep(0, "A");
        root.findDeep( 1, "A");
    }

    @Test
    void testFindDeep_EmptyDir() {
        FileNode root = new FileNode(testDataPath + "/AA/BB");

        root.findDeep( 0, "A");
    }

    @Test
    void testFindDeep_NoDir() {
        FileNode root = new FileNode(testDataPath + "/AA1");

        root.findDeep( 0, "A");
    }

    @Test
    void testFindDeep_File() {
        FileNode root = new FileNode(testDataPath + "/file1.txt");

        root.findDeep( 0, "f");
    }

    @Test
    void testFindDeep_NegativeDepth() {
        FileNode root = new FileNode(testDataPath + "/file1.txt");

        root.findDeep( -1, "f");
    }
}
