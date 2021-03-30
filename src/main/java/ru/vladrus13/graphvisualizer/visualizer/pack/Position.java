package ru.vladrus13.graphvisualizer.visualizer.pack;

import ru.vladrus13.graphvisualizer.graph.Node;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Sealed class for Packer. Class for one node
 */
public class Position {
    /**
     * How much positions empty before this node
     */
    public int skip;
    /**
     * Node class
     */
    public final Node node;
    /**
     * Numbers of position of childs
     */
    public final Collection<Integer> childs;

    /**
     * Position constructor
     *
     * @param skip how much skip positions empty before this class
     * @param node node
     */
    public Position(int skip, Node node) {
        this.skip = skip;
        this.node = node;
        childs = new ArrayList<>();
    }

    /**
     * Add child to this position
     *
     * @param child child number on Packer
     */
    public void addChild(int child) {
        childs.add(child);
    }
}
