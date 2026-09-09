import javax.swing.*;
import java.awt.*;



// https://www.geeksforgeeks.org/java/introduction-to-java-swing/
// https://docs.oracle.com/javase/tutorial/uiswing/concurrency/worker.html

public class Main {

    private static final int IMAGE_WIDTH = 600;
    private static final int IMAGE_HEIGHT = 600;


    private final JFrame window = new JFrame("Mandelbrot");

    private final DrawPanel drawPanel = new DrawPanel(IMAGE_WIDTH, IMAGE_HEIGHT);

    // https://docs.oracle.com/javase/tutorial/uiswing/components/slider.html

    private final JSlider xSlider = new JSlider(-200000, 200000, -50000);
    private final JSlider ySlider = new JSlider(-200000, 200000, 0);
    private final JSpinner zoomInput = new JSpinner(
            new SpinnerNumberModel(
                    0,
                    0,
                    13.81551056,
                    0.05
            )
    );

    private FractalRetriever fractalRetriever;

    public Main() {



        JPanel controls = new JPanel(new GridLayout(3,3,8,8));

        JLabel xValue = new JLabel(String.valueOf(xSlider.getValue() / 100000.0));
        JLabel yValue = new JLabel(String.valueOf(ySlider.getValue() / 100000.0));



        controls.add(new JLabel("X"));
        controls.add(xSlider);
        controls.add(xValue);
        controls.add(new JLabel("Y"));
        controls.add(ySlider);
        controls.add(yValue);
        controls.add(new JLabel("Zoom"));
        controls.add(zoomInput);



        JPanel mainView = new JPanel(new BorderLayout(8,8));

        // https://docs.oracle.com/javase/tutorial/uiswing/layout/border.html

        mainView.setBorder(
                BorderFactory.createEmptyBorder(8,8,8,8)
        );

        mainView.add(drawPanel, BorderLayout.CENTER);
        mainView.add(controls, BorderLayout.SOUTH);

        window.setContentPane(mainView);

        xSlider.addChangeListener(event -> {
            xValue.setText(String.valueOf(xSlider.getValue() / 100000.00));
            renderImage();
        });
        ySlider.addChangeListener(event -> {
            yValue.setText(String.valueOf(ySlider.getValue() / 100000.00));
            renderImage();
        });
        zoomInput.addChangeListener(event -> renderImage());


        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        renderImage();

    }

    // https://www.oracle.com/technical-resources/articles/javase/swingworker.html

    private void renderImage() {
        double centerX = xSlider.getValue() / 100000.0;
        double centerY = ySlider.getValue() / 100000.0;

        // Zooming linearly felt slow, raising e to the power of the zoom input makes zoom feel more natural.
        double zoom = Math.pow(2.71828, (Double) zoomInput.getValue());

        if (fractalRetriever != null && !fractalRetriever.isDone()) {
            fractalRetriever.cancel(true);
        }

        Mandelbrot mandelbrot = new Mandelbrot(IMAGE_WIDTH,IMAGE_HEIGHT,centerX,centerY,zoom);

        fractalRetriever = new FractalRetriever(mandelbrot, drawPanel);
        fractalRetriever.execute();
    }

    static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Main();
        }
        );
    }

}
