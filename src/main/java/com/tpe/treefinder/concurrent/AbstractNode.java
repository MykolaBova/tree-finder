package com.tpe.treefinder.concurrent;

import java.util.Deque;
import java.util.LinkedList;

public abstract class AbstractNode implements INode {

    protected int depth;

    @Override
    public final boolean containsMask(String mask) {
        return getName().contains(mask);
    }

    @Override
    public final void findDeep(int depth, String mask) {
        Deque<INode> stack = new LinkedList<>();

        stack.push(this);

        while (!stack.isEmpty()) {
            INode node = stack.pop();

            if(node.containsMask(mask) && node.getDepth() <= depth) {
                node.printFullPath();
            }

            for(INode child : node.getChildren()) {
                if(child.getDepth() <= depth) {
                    stack.push(child);
                } else {
                    break;
                }
            }
        }
    }

    @Override
    public int getDepth() {
        return this.depth;
    }
}
