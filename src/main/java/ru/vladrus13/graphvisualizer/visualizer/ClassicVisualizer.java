package ru.vladrus13.graphvisualizer.visualizer;

import ru.vladrus13.graphvisualizer.graph.Graph;
import ru.vladrus13.graphvisualizer.graph.Node;
import ru.vladrus13.graphvisualizer.visualizer.pack.Packer;
import ru.vladrus13.graphvisualizer.visualizer.pack.Position;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;

public class ClassicVisualizer implements Visualizer<BufferedImage> {

    public final boolean isClever;
    public final static int SQUARE_SIZE = 50;

    public ClassicVisualizer(boolean isClever) {
        this.isClever = isClever;
    }

    public double fontSize(Graphics graphics, String str) {
        double l = 0, r = SQUARE_SIZE, m;
        while (r - l > 1e-2) {
            m = (r + l) / 2;
            graphics.setFont(graphics.getFont().deriveFont((float) m));
            if (graphics.getFontMetrics().stringWidth(str) > SQUARE_SIZE) {
                r = m;
            } else {
                l = m;
            }
        }
        return l;
    }

    @Override
    public BufferedImage getImage(final Graph graph) {
        Packer packer = new Packer(graph, isClever);
        packer.run();
        ArrayList<ArrayList<Position>> pack = packer.getPack();
        BufferedImage bufferedImage = new BufferedImage(
                packer.getMaxWidth() * SQUARE_SIZE,
                SQUARE_SIZE * (pack.size() * 2),
                BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImage.createGraphics();
        graphics.setColor(Color.WHITE);
        for (int level = 0; level < pack.size(); level++) {
            ArrayList<Position> oneLevel = pack.get(level);
            int position = 0;
            for (Position node : oneLevel) {
                position += node.skip;
                if (node.node == null) {
                    continue;
                }
                int rectangleStartX = position * SQUARE_SIZE;
                int rectangleStartY = level * 2 * SQUARE_SIZE;
                graphics.drawRect(rectangleStartX, rectangleStartY, SQUARE_SIZE, SQUARE_SIZE);
                graphics.setFont(graphics.getFont().deriveFont((float) fontSize(graphics, node.node.getName()) * 2 / 3));
                int fontStartY = (graphics.getFont().getSize() + SQUARE_SIZE) / 2 + rectangleStartY;
                int fontStartX = (SQUARE_SIZE - graphics.getFontMetrics().stringWidth(node.node.getName())) / 2;
                graphics.drawString(node.node.getName(), rectangleStartX + fontStartX, fontStartY);
                for (Integer childs : node.childs) {
                    int childStartX = childs * SQUARE_SIZE;
                    int childStartY = (level * 2 + 2) * SQUARE_SIZE;
                    graphics.drawLine(rectangleStartX + SQUARE_SIZE / 2, rectangleStartY + SQUARE_SIZE,
                            childStartX + SQUARE_SIZE / 2, childStartY);
                }
                position++;
            }
        }
        return bufferedImage;
    }

    @Override
    public void saveImage(Graph graph, Path path, String name) throws IOException {
        ImageIO.write(getImage(graph), "PNG", new FileOutputStream(String.valueOf(path.resolve(name + ".png"))));
    }

    @Override
    public String toClassString() {
        return this.getClass().getSimpleName() + isClever;
    }
}
