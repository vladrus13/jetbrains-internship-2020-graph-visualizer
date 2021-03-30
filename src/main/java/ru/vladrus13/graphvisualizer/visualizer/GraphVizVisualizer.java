package ru.vladrus13.graphvisualizer.visualizer;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableNode;
import guru.nidi.graphviz.model.Node;
import ru.vladrus13.graphvisualizer.graph.Graph;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static guru.nidi.graphviz.model.Factory.*;

public class GraphVizVisualizer implements Visualizer<BufferedImage> {

    public MutableNode getNode(ru.vladrus13.graphvisualizer.graph.Node node) {
        MutableNode returned = mutNode(node.getName());
        for (ru.vladrus13.graphvisualizer.graph.Node child : node.getChilds()) {
            returned.addLink(getNode(child));
        }
        return returned;
    }

    @Override
    public BufferedImage getImage(final Graph graph) {
        return Graphviz.fromGraph(graph().with(getNode(graph.getRoot()))).render(Format.PNG).toImage();
    }

    @Override
    public void saveImage(Graph graph, Path path, String name) throws IOException {
        Graphviz.fromGraph(graph().with(getNode(graph.getRoot()))).render(Format.PNG).toFile(path.resolve(name + ".png").toFile());
    }

    @Override
    public String toClassString() {
        return this.getClass().getSimpleName();
    }
}
