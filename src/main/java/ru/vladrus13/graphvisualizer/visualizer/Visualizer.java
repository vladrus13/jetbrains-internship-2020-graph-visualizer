package ru.vladrus13.graphvisualizer.visualizer;

import ru.vladrus13.graphvisualizer.graph.Graph;

import java.awt.image.BufferedImage;

public interface Visualizer {
    BufferedImage getImage(Graph graph);
}
