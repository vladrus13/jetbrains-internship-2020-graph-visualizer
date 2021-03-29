package ru.vladrus13.graphvisualizer.visualizer;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableNode;
import guru.nidi.graphviz.model.Node;
import ru.vladrus13.graphvisualizer.graph.Graph;

import java.awt.image.BufferedImage;

import static guru.nidi.graphviz.model.Factory.*;

public class GraphVizVisualizer implements Visualizer{

    public MutableNode getNode(ru.vladrus13.graphvisualizer.graph.Node node) {
        MutableNode returned = mutNode(node.getName());
        for (ru.vladrus13.graphvisualizer.graph.Node child : node.getChilds()) {
            returned.addLink(getNode(child));
        }
        return returned;
    }

    @Override
    public BufferedImage getImage(Graph graph) {
        return Graphviz.fromGraph(graph().with(getNode(graph.getRoot()))).render(Format.PNG).toImage();
    }
}
