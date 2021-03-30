package ru.vladrus13.graphvisualizer.visualizer;

import ru.vladrus13.graphvisualizer.graph.Graph;
import ru.vladrus13.graphvisualizer.graph.Node;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Visualize a graph with symbols like:
 * {@code |-----a----|
 * |--b-||--c-|
 * |d||e||f||g|}
 */
public class ASCIIVisualizer implements Visualizer<String> {

    /**
     * Recursive fill a lines of graph
     *
     * @param lines container of lines
     * @param node  current node
     * @param level current distance from root
     * @param start start position for this node
     * @return size of this node in ASCII
     */
    private int fill(ArrayList<StringBuilder> lines, Node node, int level, int start) {
        if (level + 1 > lines.size()) {
            lines.add(new StringBuilder());
        }
        if (start > lines.get(level).length()) {
            lines.get(level).append(" ".repeat(start - lines.get(level).length()));
        }
        int localStart = start;
        for (Node child : node.getChilds()) {
            int localSize = fill(lines, child, level + 1, localStart);
            localStart += localSize;
        }
        int size = localStart - start;
        int realSize = 2 + node.getName().length();
        int leftBorder = 0;
        int rightBorder = 0;
        if (size > realSize) {
            leftBorder = (size - realSize + 1) / 2;
            rightBorder = (size - realSize) / 2;
        }
        lines.get(level)
                .append("|")
                .append("-".repeat(leftBorder))
                .append(node.getName())
                .append("-".repeat(rightBorder))
                .append("|");
        return Math.max(size, realSize);
    }

    @Override
    public String getImage(final Graph graph) {
        ArrayList<StringBuilder> lines = new ArrayList<>();
        fill(lines, graph.getRoot(), 0, 0);
        return String.join("\n", lines);
    }

    @Override
    public void saveImage(Graph graph, Path path, String name) throws IOException {
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path.resolve(name + ".txt"))) {
            bufferedWriter.write(getImage(graph));
        }
    }

    @Override
    public String toClassString() {
        return this.getClass().getSimpleName();
    }
}
