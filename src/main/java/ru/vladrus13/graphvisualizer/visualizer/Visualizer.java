package ru.vladrus13.graphvisualizer.visualizer;

import ru.vladrus13.graphvisualizer.graph.Graph;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;


public interface Visualizer<T> {
    T getImage(final Graph graph);

    void saveImage(final Graph graph, Path path, String name) throws IOException;

    String toClassString();
}
