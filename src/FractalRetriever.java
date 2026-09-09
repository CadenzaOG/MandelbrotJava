import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutionException;

public class FractalRetriever extends SwingWorker<BufferedImage, Void> {

    private Fractal fractal;
    private DrawPanel drawPanel;

    public FractalRetriever(Fractal fractal, DrawPanel drawPanel) {
        this.fractal = fractal;
        this.drawPanel = drawPanel;
    }
    @Override
    protected BufferedImage doInBackground() throws Exception {
        return this.fractal.renderFrame();
    }

    @Override
    protected void done() {
        if (isCancelled()) {
            return;
        }
        try {
            BufferedImage image = get();
            drawPanel.setImage(image);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        catch (ExecutionException e) {
            e.getCause().printStackTrace();
        }
    }
};

