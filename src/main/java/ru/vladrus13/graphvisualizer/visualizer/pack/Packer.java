package ru.vladrus13.graphvisualizer.visualizer.pack;

import ru.vladrus13.graphvisualizer.graph.Graph;
import ru.vladrus13.graphvisualizer.graph.Node;

import java.util.ArrayList;

/**
 * Pack graph to table
 */
public class Packer {

    /**
     * Packing graph
     */
    private final Graph graph;
    /**
     * Pack
     */
    private ArrayList<ArrayList<Position>> pack;
    /**
     * First not null position of every line of tree
     */
    private ArrayList<Integer> firstNotNullPosition;
    /**
     * Is packing clever
     */
    private final boolean isClever;
    /**
     * Max width of pack
     */
    private int maxWidth = 0;

    /**
     * Container for Packer class
     *
     * @param graph    ready graph
     * @param isClever is clever packing or not
     */
    public Packer(final Graph graph, final boolean isClever) {
        this.graph = graph;
        this.pack = null;
        firstNotNullPosition = null;
        this.isClever = isClever;
    }

    /**
     * Recursive function of packing
     *
     * @param level    level of DFS
     * @param position position on level
     * @param node     node of graph
     * @return if clever pack: size, else: left position, of node
     */
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
            return size;
        }
    }

    /**
     * Run packing
     */
    public void run() {
        pack = new ArrayList<>();
        firstNotNullPosition = new ArrayList<>();
        pack(0, 0, graph.getRoot());
    }

    /**
     * Getting pack of graph
     *
     * @return pack of the graph
     */
    public ArrayList<ArrayList<Position>> getPack() {
        return pack;
    }

    /**
     * Get max width of graph
     *
     * @return max width of graph
     */
    public int getMaxWidth() {
        return maxWidth;
    }
}
