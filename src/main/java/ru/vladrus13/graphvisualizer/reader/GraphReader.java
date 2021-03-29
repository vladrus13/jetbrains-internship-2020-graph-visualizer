package ru.vladrus13.graphvisualizer.reader;

import ru.vladrus13.graphvisualizer.graph.Graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class GraphReader {

    private final static Logger logger = Logger.getLogger(GraphReader.class.getName());

    public static Graph readGraph(Path path) {
        Graph graph = new Graph();
        try (BufferedReader bufferedReader = Files.newBufferedReader(path)) {
            bufferedReader.lines().forEach(line -> {
                String[] nodes = line.split("-");
                if (nodes.length == 1) {
                    graph.setParent(nodes[0].trim());
                } else {
                    if (nodes.length != 2) {
                        logger.warning("Problem in line: " + line + ". Wrong edge. It can be only one \"-\" in one line");
                    }
                    graph.addEdge(nodes[0].trim(), nodes[1].trim());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return graph;
    }
}
