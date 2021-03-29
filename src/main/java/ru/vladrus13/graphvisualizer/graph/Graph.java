package ru.vladrus13.graphvisualizer.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class Graph {

    private final static Logger logger = Logger.getLogger(Graph.class.getName());

    private final Map<String, Node> nodes;
    private Node root;

    public Graph() {
        nodes = new HashMap<>();
    }

    public void addNodes(Collection<String> nodesCollection) {
        for (String node : nodesCollection) {
            nodes.put(node, new Node(node, ArrayList::new));
        }
    }

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
        child.setParent(parentNode);
    }

    public void setParent(String parent) {
        if (!nodes.containsKey(parent)) {
            nodes.put(parent, new Node(parent, ArrayList::new));
        }
        this.root = nodes.get(parent);
    }

    public Node getRoot() {
        return root;
    }
}
