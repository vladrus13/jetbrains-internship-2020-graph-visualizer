package ru.vladrus13.graphvisualizer.visualizer.pack;

import ru.vladrus13.graphvisualizer.graph.Graph;
import ru.vladrus13.graphvisualizer.graph.Node;

import java.util.ArrayList;

public class Packer {

    private final Graph graph;
    private ArrayList<ArrayList<Position>> pack;
    private ArrayList<Integer> firstNotNullPosition;
    private final boolean isClever;
    private int maxWidth = 0;

    public Packer(final Graph graph, final boolean isClever) {
        this.graph = graph;
        this.pack = null;
        firstNotNullPosition = null;
        this.isClever = isClever;
    }

    private int pack(final int level, int position, final Node node) {
        if (pack.size() < level + 1) {
            pack.add(new ArrayList<>());
            firstNotNullPosition.add(0);
        }
        if (!isClever) {
            int skip = 0;
            if (firstNotNullPosition.get(level) < position) {
                skip = position - firstNotNullPosition.get(level);
            }
            position = Math.max(position, firstNotNullPosition.get(level));
            Position position1 = new Position(skip, node);
            pack.get(level).add(position1);
            firstNotNullPosition.set(level, position + 1);
            for (Node child : node.getChilds()) {
                position1.addChild(pack(level + 1, 0, child));
            }
            maxWidth = Math.max(maxWidth, position + 1);
            position1.size = position;
            return position;
        } else {
            Position us = new Position(0, node);
            int size = 0;
            for (Node child : node.getChilds()) {
                int currentSize = pack(level + 1, position + size, child);
                us.addChild(position + size + (currentSize - 1) / 2);
                size += currentSize;
            }
            us.skip = Math.max(0, position - firstNotNullPosition.get(level)) + (size - 1) / 2;
            Position back = pack.get(level).size() == 0 ? null : pack.get(level).get(pack.get(level).size() - 1);
            if (back != null && back.node == null) {
                us.skip += back.skip;
                pack.get(level).set(pack.get(level).size() - 1, us);
            } else {
                pack.get(level).add(us);
            }
            if (size / 2 > 0) {
                pack.get(level).add(new Position(size / 2, null));
            }
            size = Math.max(1, size);
            firstNotNullPosition.set(level, position + size);
            maxWidth = Math.max(maxWidth, size);
            us.size = size;
            return size;
        }
    }

    public void run() {
        pack = new ArrayList<>();
        firstNotNullPosition = new ArrayList<>();
        pack(0, 0, graph.getRoot());
    }

    public ArrayList<ArrayList<Position>> getPack() {
        return pack;
    }

    public int getMaxWidth() {
        return maxWidth;
    }
}
