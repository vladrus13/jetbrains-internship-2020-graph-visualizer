package ru.vladrus13.graphvisualizer.graph;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * Node class
 *
 * @author vladrus13 on 29.03.2021
 **/
public class Node {
    /**
     * Name of node
     */
    private final String name;
    /**
     * Childs of this node
     */
    private final Collection<Node> childs;

    /**
     * Constructor of Node class
     *
     * @param name        name of this node
     * @param constructor constructor of childs collection
     */
    public Node(String name, Supplier<Collection<Node>> constructor) {
        this.name = name;
        childs = constructor.get();
    }

    /**
     * Add child to childs of node
     *
     * @param node child node
     */
    public void addChild(Node node) {
        childs.add(node);
    }

    /**
     * Get childs of node
     *
     * @return childs of node
     */
    public Collection<Node> getChilds() {
        return childs;
    }

    /**
     * Get name of node
     *
     * @return name of node
     */
    public String getName() {
        return name;
    }
}
