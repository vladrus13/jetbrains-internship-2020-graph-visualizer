package ru.vladrus13.graphvisualizer.visualizer.pack;

import ru.vladrus13.graphvisualizer.graph.Graph;
import ru.vladrus13.graphvisualizer.graph.Node;

import java.util.ArrayList;

public class Packer {

    private final Graph graph;
    private ArrayList<ArrayList<Position>> pack;
    private ArrayList<Integer> firstNotNullPosition;
    private final boolean isClever;

    public Packer(Graph graph, boolean isClever) {
        this.graph = graph;
        this.pack = null;
        firstNotNullPosition = null;
        this.isClever = isClever;
    }

    private int pack(int level, int position, Node node) {
        if (pack.size() < level + 1) {
            pack.add(new ArrayList<>());
            firstNotNullPosition.add(0);
        }
        int skip = 0;
        if (firstNotNullPosition.get(level) < position) {
            skip = position - firstNotNullPosition.get(level);
        }
        position = Math.max(position, firstNotNullPosition.get(level));
        Position position1 = new Position(skip, node);
        pack.get(level).add(position1);
        int childPosition = (isClever ? position - node.getChilds().size() / 2 : 0);
        for (Node child : node.getChilds()) {
            position1.addChild(pack(level + 1, childPosition, child));
        }
        return position;
    }

    public void run() {
        pack = new ArrayList<>();
        firstNotNullPosition = new ArrayList<>();
        pack(0, 0, graph.getRoot());
    }

    public ArrayList<ArrayList<Position>> getPack() {
        return pack;
    }
}
