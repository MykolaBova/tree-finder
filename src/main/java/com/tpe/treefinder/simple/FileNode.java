package com.tpe.treefinder.simple;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class FileNode extends AbstractNode {

    File file;

    public FileNode(String pathName) {
        this(null, pathName, null);
    }

    public FileNode(INode root, String pathName, String fileName) {

        if(root == null) {
            this.file = new File(pathName);
            super.depth = 0;
        } else {
            this.file = new File(pathName, fileName);
            super.depth = root.getDepth() + 1;
        }
    }
    
    @Override
    public String getName() {
        return file.getName();
    }

    @Override
    public String getPath() {
        return file.getPath();
    }

    @Override
    public List<INode> getChildren() {
        List<INode> children = new LinkedList<>();
        String[] filenames = file.list();

        if (filenames == null) { // no child nodes
            return Collections.emptyList();
        }

        for (String filename : filenames) {
            FileNode childNode = new FileNode(this, this.getPath(), filename);
            children.add(childNode);
        }

        return children;
    }

    @Override
    public void printFullPath() {
        if(file.exists()) {
            System.out.println("path=" + this.getPath()
                    + " name=" + this.getName());
        }
    }
}
