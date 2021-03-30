package ru.vladrus13.graphvisualizer.frame;

import ru.vladrus13.graphic.Graphics;
import ru.vladrus13.jgraphic.basic.Frame;
import ru.vladrus13.jgraphic.basic.event.returned.ReturnEvent;
import ru.vladrus13.jgraphic.basic.event.returned.ReturnInt;
import ru.vladrus13.jgraphic.bean.Point;
import ru.vladrus13.jgraphic.bean.Size;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * Frame to display image
 */
public class OnlineFrame extends Frame {

    /**
     * Image to display
     */
    private final BufferedImage image;

    /**
     * Standard constructor for Frame
     *
     * @param name          system name of frame
     * @param start         start position for frame
     * @param size          frame size
     * @param parent        parent frame
     * @param bufferedImage image to display
     */
    public OnlineFrame(String name, Point start, Size size, Frame parent, BufferedImage bufferedImage) {
        super(name, start, size, parent);
        this.image = bufferedImage;
        recalculateChildes();
    }

    @Override
    protected void nonCheckingDraw(Graphics graphics) {
        if (image != null) {
            graphics.drawImage(image, start.x, start.y, size.x, size.y);
        }
    }

    @Override
    public ReturnEvent keyPressed(KeyEvent e) {
        return new ReturnInt(ReturnInt.NOTHING);
    }

    @Override
    public ReturnEvent mousePressed(MouseEvent e) {
        return new ReturnInt(ReturnInt.NOTHING);
    }
}
