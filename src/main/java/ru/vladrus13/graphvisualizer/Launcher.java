package ru.vladrus13.graphvisualizer;

import ru.vladrus13.graphvisualizer.frame.OnlineApp;
import ru.vladrus13.graphvisualizer.graph.Graph;
import ru.vladrus13.graphvisualizer.reader.GraphReader;
import ru.vladrus13.graphvisualizer.visualizer.ASCIIVisualizer;
import ru.vladrus13.graphvisualizer.visualizer.ClassicVisualizer;
import ru.vladrus13.graphvisualizer.visualizer.GraphVizVisualizer;
import ru.vladrus13.graphvisualizer.visualizer.Visualizer;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * Launcher class
 *
 * @author vladrus13 on 29.03.2021
 **/
public class Launcher {

    /**
     * Logger for main class
     */
    public static final Logger logger = Logger.getLogger(Launcher.class.getName());
    /**
     * Resources path for test variant
     */
    public static final Path resources = Path.of("src").resolve("main").resolve("resources").resolve("examples");

    /**
     * Launcher for app
     * Usage: mvn package parameters from root folder or java -jar name.jar parameters
     * Parameters:
     * --online - Online drawing
     * --graphviz, --cleverclassic, --classic, --ascii - visualizations
     * --input=PATH - input path to file
     * --output=DIRECTORY - output path
     *
     * @param args arguments
     * @throws IOException if we have problem with reading from or writing to file system
     */
    public static void main(String[] args) throws IOException {
        if (args == null || args.length == 0) {
            logger.info("Start tests...");
            Collection<Visualizer<?>> visualizers = Arrays.asList(
                    new GraphVizVisualizer(),
                    new ClassicVisualizer(false),
                    new ClassicVisualizer(true),
                    new ASCIIVisualizer());
            try (Stream<Path> paths = Files.walk(resources)) {
                paths.filter(Files::isRegularFile).forEach(path -> {
                    logger.info("--- Read: " + path.toString());
                    Graph graph = GraphReader.readGraph(path);
                    Path parentDirectory = path.getParent().getParent().resolve("images");
                    try {
                        Files.createDirectories(parentDirectory);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String nameFile = path.toFile().getName();
                    for (Visualizer<?> visualizer : visualizers) {
                        try {
                            visualizer.saveImage(graph, parentDirectory, visualizer.toClassString() + nameFile);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Visualizer<?> visualizer = null;
            Path input = null;
            Path output = null;
            boolean online = false;
            boolean help = false;
            for (String arg : args) {
                if (arg.startsWith("--input=")) {
                    input = Path.of(arg.substring(8));
                }
                if (arg.startsWith("--output=")) {
                    output = Path.of(arg.substring(9));
                }
                switch (arg) {
                    case "--online":
                        online = true;
                        break;
                    case "--graphviz":
                        visualizer = new GraphVizVisualizer();
                        break;
                    case "--cleverclassic":
                        visualizer = new ClassicVisualizer(true);
                        break;
                    case "--classic":
                        visualizer = new ClassicVisualizer(false);
                        break;
                    case "--ascii":
                        visualizer = new ASCIIVisualizer();
                        break;
                    case "--help":
                        help = true;
                        break;
                }
            }
            if (help) {
                logger.info("Usage: mvn package <parameters> from root folder or java -jar name.jar <parameters>.\n" +
                        "Parameters:\n " +
                        "--online - Online drawing\n" +
                        "--graphviz, --cleverclassic, --classic, --ascii - visualizations\n" +
                        "--input=PATH - input path to file\n" +
                        "--output=DIRECTORY - output path");
            }
            if (visualizer == null) {
                logger.info("Visualizer is not chosen. Exit");
                return;
            }
            if (input == null) {
                logger.info("Input path is not chosen. Exit");
                return;
            }
            Graph graph = GraphReader.readGraph(input);
            if (output != null) {
                visualizer.saveImage(graph, output, "graph");
                logger.info("Output is chosen. Write to path");
            }
            if (online) {
                if (visualizer instanceof GraphVizVisualizer ||
                        visualizer instanceof ClassicVisualizer) {
                    BufferedImage bufferedImage = (BufferedImage) visualizer.getImage(graph);
                    new OnlineApp(800, 800, bufferedImage);
                } else {
                    logger.info("Can't draw online this type of visualization");
                }
            }
        }
    }
}
