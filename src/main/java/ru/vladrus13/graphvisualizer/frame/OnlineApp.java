package ru.vladrus13.graphvisualizer.frame;

import ru.vladrus13.jgraphic.App;
import ru.vladrus13.jgraphic.bean.CoordinatesType;
import ru.vladrus13.jgraphic.bean.Point;
import ru.vladrus13.jgraphic.bean.Size;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * App to display visualization
 */
public class OnlineApp extends App {
    /**
     * @param width         width of app
     * @param height        height of app
     * @param bufferedImage image to display
     */
    public OnlineApp(int width, int height, BufferedImage bufferedImage) {
        super(width, height);
        current = new OnlineFrame("Frame",
                new Point(0, 0, CoordinatesType.REAL),
                new Size(width, height, CoordinatesType.REAL), null, bufferedImage);
        painter();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
