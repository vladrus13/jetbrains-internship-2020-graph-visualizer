package ru.vladrus13.graphvisualizer;

import ru.vladrus13.graphvisualizer.graph.Graph;
import ru.vladrus13.graphvisualizer.reader.GraphReader;
import ru.vladrus13.graphvisualizer.visualizer.ClassicVisualizer;
import ru.vladrus13.graphvisualizer.visualizer.GraphVizVisualizer;
import ru.vladrus13.graphvisualizer.visualizer.Visualizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Launcher class
 * @author vladrus13 on 29.03.2021
 **/
public class Launcher {

    public static void main(String[] args) {
        Visualizer visualizer = new ClassicVisualizer(true);
        Graph graph = GraphReader.readGraph(Path.of("src").resolve("main").resolve("resources").resolve("examples").resolve("1.txt"));
        BufferedImage bufferedImage = visualizer.getImage(graph);
        try {
            ImageIO.write(bufferedImage, "PNG", new File("src/main/resources/images/heh.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
