package ru.vladrus13.graphvisualizer.visualizer.pack;

import ru.vladrus13.graphvisualizer.graph.Node;

import java.util.ArrayList;
import java.util.Collection;

public class Position {
    public final int skip;
    public final Node node;
    public final Collection<Integer> childs;

    public Position(int skip, Node node) {
        this.skip = skip;
        this.node = node;
        childs = new ArrayList<>();
    }

    public void addChild(int child) {
        childs.add(child);
    }
}
