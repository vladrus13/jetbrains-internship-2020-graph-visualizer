package ru.vladrus13.graphvisualizer.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Graph class to tree
 */
public class Graph {

    /**
     * Nodes of graph
     */
    private final Map<String, Node> nodes;
    /**
     * Root of tree
     */
    private Node root;

    /**
     * Graph constructor
     */
    public Graph() {
        nodes = new HashMap<>();
    }

    /**
     * Add edge and node to graph
     *
     * @param name   name of adding node
     * @param parent parent of this node
     */
    public void addEdge(String name, String parent) {
        if (!nodes.containsKey(name)) {
            nodes.put(name, new Node(name, ArrayList::new));
        }
        if (!nodes.containsKey(parent)) {
            nodes.put(parent, new Node(parent, ArrayList::new));
        }
        Node child = nodes.get(name);
        Node parentNode = nodes.get(parent);
        parentNode.addChild(child);
    }

    /**
     * Set root of this graph
     *
     * @param root root of graph
     */
    public void setRoot(String root) {
        if (!nodes.containsKey(root)) {
            nodes.put(root, new Node(root, ArrayList::new));
        }
        this.root = nodes.get(root);
    }

    /**
     * Get root of graph
     *
     * @return root of graph
     */
    public Node getRoot() {
        return root;
    }
}
