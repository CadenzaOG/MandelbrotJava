import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

// https://docs.oracle.com/javase/tutorial/uiswing/events/mouselistener.html

// https://www.d.umn.edu/~gshute/java/swing/graphics.xhtml

//https://docs.oracle.com/javase/tutorial/uiswing/painting/step2.html

public class DrawPanel extends JPanel {

    private BufferedImage image;

    public DrawPanel(int width, int height) {
        setPreferredSize(new Dimension(width, height));
    }

    public void setImage(BufferedImage image) {
        this.image = image;
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (image != null) {
            int x = ((getWidth() - image.getWidth()) / 2);
            int y = ((getHeight() - image.getHeight()) / 2);
            g.drawImage(image,x,y, null);
        }


    }
}
