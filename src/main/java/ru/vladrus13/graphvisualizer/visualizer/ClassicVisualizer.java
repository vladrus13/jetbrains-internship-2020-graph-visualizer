package ru.vladrus13.graphvisualizer.visualizer;

import ru.vladrus13.graphvisualizer.graph.Graph;
import ru.vladrus13.graphvisualizer.visualizer.pack.Packer;
import ru.vladrus13.graphvisualizer.visualizer.pack.Position;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

public class ClassicVisualizer implements Visualizer {

    public final boolean isClever;
    public final static int SQUARE_SIZE = 200;
    public ArrayList<Integer> firstNotNullPosition;

    public ClassicVisualizer(boolean isClever) {
        this.isClever = isClever;
    }

    public double fontSize(Graphics graphics, String str) {
        double l = 0, r = SQUARE_SIZE, m;
        while (r - l > 1e-2) {
            m = (r + l) / 2;
            if (graphics.getFontMetrics().stringWidth(str) > SQUARE_SIZE) {
                r = m;
            } else {
                l = m;
            }
        }
        return l;
    }

    @Override
    public BufferedImage getImage(Graph graph) {
        Packer packer = new Packer(graph, isClever);
        packer.run();
        ArrayList<ArrayList<Position>> pack = packer.getPack();
        firstNotNullPosition = new ArrayList<>(Collections.nCopies(pack.size(), 0));
        BufferedImage bufferedImage = new BufferedImage(SQUARE_SIZE * pack.size(),
                (pack.stream().mapToInt(ArrayList::size).max().orElseThrow() * 2 + 1) * SQUARE_SIZE, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImage.createGraphics();
        graphics.setColor(Color.WHITE);
        for (int level = 0; level < pack.size(); level++) {
            ArrayList<Position> oneLevel = pack.get(level);
            int position = 0;
            for (Position node : oneLevel) {
                int rectangleStartX = position * SQUARE_SIZE;
                int rectangleStartY = level * 2 * SQUARE_SIZE;
                graphics.drawRect(rectangleStartX, rectangleStartY, SQUARE_SIZE, SQUARE_SIZE);
                graphics.setFont(graphics.getFont().deriveFont((float) fontSize(graphics, node.node.getName()) * 2 / 3));
                int fontStartY = (graphics.getFont().getSize() + SQUARE_SIZE) / 2 + rectangleStartY;
                graphics.drawString(node.node.getName(), rectangleStartX, fontStartY);
                for (Integer childs : node.childs) {
                    int childStartX = childs * SQUARE_SIZE;
                    int childStartY = (level * 2 + 2) * SQUARE_SIZE;
                    graphics.drawLine(rectangleStartX + SQUARE_SIZE / 2, rectangleStartY + SQUARE_SIZE,
                            childStartX + SQUARE_SIZE / 2, childStartY);
                }
                position += node.skip + 1;
            }
        }

        return bufferedImage;
    }
}
