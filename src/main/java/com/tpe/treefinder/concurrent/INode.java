package com.tpe.treefinder.concurrent;

import java.util.List;

/**
 * Generic interface for all nodes in tree-finder tree
 */
public interface INode {
    /**
     *
     * @return Node name
     */
    String getName();

    /**
     *
     * @return full path to the current @INode
     */
    String getPath();

    /**
     *
     * @return level of the current @INode
     */
    int getDepth();

    /**
     *
     * @param mask mask of the @INode name
     * @return does the current @INode name contain mask
     */
    boolean containsMask(String mask);

    /**
     *
     * @return list of childs of the current @INode
     */
    List<INode> getChildren();

    /**
     * print the full name of the current node
     */
    void printFullPath();

    /**
     * find all nodes with the given mask in name
     */
    void findDeep(int depth, String mask);
}
