package ru.vladrus13.graphvisualizer.graph;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * Node class
 * @author vladrus13 on 29.03.2021
 **/
public class Node {
    private final String name;
    private final Collection<Node> childs;
    private Node parent;

    public Node(String name, Supplier<Collection <Node>> constructor) {
        this.name = name;
        childs = constructor.get();
    }

    public void addChild(Node node) {
        childs.add(node);
    }

    public Collection<Node> getChilds() {
        return childs;
    }

    public String getName() {
        return name;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Node getParent() {
        return parent;
    }
}
