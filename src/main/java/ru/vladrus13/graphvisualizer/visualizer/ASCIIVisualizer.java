package ru.vladrus13.graphvisualizer.visualizer;

import ru.vladrus13.graphvisualizer.graph.Graph;
import ru.vladrus13.graphvisualizer.graph.Node;
import ru.vladrus13.graphvisualizer.visualizer.pack.Packer;
import ru.vladrus13.graphvisualizer.visualizer.pack.Position;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ASCIIVisualizer implements Visualizer<String> {

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
