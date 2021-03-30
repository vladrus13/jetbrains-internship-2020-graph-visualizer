package ru.vladrus13.graphvisualizer.visualizer;

import ru.vladrus13.graphvisualizer.graph.Graph;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Visualizer class for graph
 *
 * @param <T> what result of visualization
 */
public interface Visualizer<T> {
    /**
     * Get image of graph
     *
     * @param graph graph
     * @return image of graph
     */
    T getImage(final Graph graph);

    /**
     * Save "image" of graph to directory with name
     *
     * @param graph graph to save
     * @param path  path to directory to save
     * @param name  name of file
     * @throws IOException if we have problem with writing to file system
     */
    void saveImage(final Graph graph, Path path, String name) throws IOException;

    /**
     * Get a class name with types
     *
     * @return class name with types
     */
    String toClassString();
}
