import org.w3c.dom.css.RGBColor;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.classfile.CompoundElement;

import static java.awt.Color.*;

//https://docs.oracle.com/javase/tutorial/essential/concurrency/interrupt.html

public class Mandelbrot implements Fractal {

    private ComplexNumber z;

    private int maxIterations;
    private int height;
    private int width;
    private int cPlane;
    private double zoom;
    private double centerX;
    private double centerY;
    private ComplexNumber min;
    private ComplexNumber max;
    private int[] colors = new int[]{
            0xFFFFFE,
            0xFFFEF4,
            0xFCFFEA,
            0xF2FFDF,
            0xDEFAD1,
            0xBFEDBD,
            0xAAE0B6,
            0x98D4B5,
            0x87C7B8,
            0x77B8BA,
            0x6898AD,
            0x5A78A1,
            0x4D5894,
            0x494187,
            0x50367A,
            0x572C6E,
            0x5C2361,
            0x541B49,
            0x47142F,
            0x3B0E1A,
            0x2E0909,
            0x210D05,
            0x140C02,
            0x080601
    };


    public Mandelbrot(int width, int height, double centerX, double centerY, double zoom) {
        this.width = width;
        this.height = height;
        this.zoom = zoom;
        this.centerX = centerX;
        this.centerY = centerY;

        this.maxIterations = 100;
        this.cPlane = 2;
        this.min = new ComplexNumber(centerX-cPlane/zoom,centerY-cPlane/zoom);
        this.max = new ComplexNumber(centerX+cPlane/zoom,centerY+cPlane/zoom);

    }

    private void init() {

    }

    private double map(double n,
                       double inMin,
                       double inMax,
                       double outMin,
                       double outMax) {
        double inRange = inMax-inMin;
        double outRange = outMax-outMin;
        double inPos = n - inMin;
        double proportion = inPos/inRange;
        double outPos = proportion * outRange;

        return outPos + outMin;
    }

    @Override
    public BufferedImage renderFrame() throws InterruptedException {

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < this.width; x++) {

            double real = map(x,0,this.width,this.min.getReal(), this.max.getReal());

            for (int y = 0; y < this.height; y++) {

                double imaginary =  map(y, 0, this.height, this.min.getImaginary(), this.max.getImaginary());

                ComplexNumber z = new ComplexNumber();
                ComplexNumber c = new ComplexNumber(real,imaginary);

                // To add: additional maths for quickly checking if point in main cardiod or bulb
                boolean cardioid = false;
                boolean bulb = false;

                int iterations = 0;

                while (iterations < maxIterations) {

                    double magnitude = z.getMagnitude();

                    if (magnitude > 4) {
                        break;
                    }

                    z = z.square();
                    z = z.add(c);

                    iterations++;
                }


                int rgb = colors[(int) map((double) iterations/maxIterations,0,1,0,colors.length - 1)];

                if (iterations == maxIterations) {
                    rgb = 0x000000;
                }

                image.setRGB(x, y, rgb);
            }
        }
        return image;
    }
}
