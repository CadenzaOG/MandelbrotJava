

import java.awt.*;
import java.awt.image.BufferedImage;


//https://docs.oracle.com/javase/tutorial/essential/concurrency/interrupt.html

public class Mandelbrot implements Fractal {

    private ComplexNumber z;

    private int maxIterations;
    private int height;
    private int width;
    private double cPlane;
    private double zoom;
    private double centerX;
    private double centerY;
    private ComplexNumber min;
    private ComplexNumber max;
//    private int[] colors = new int[]{
//            0xFFFFFE,
//            0xFFFEF4,
//            0xFCFFEA,
//            0xF2FFDF,
//            0xDEFAD1,
//            0xBFEDBD,
//            0xAAE0B6,
//            0x98D4B5,
//            0x87C7B8,
//            0x77B8BA,
//            0x6898AD,
//            0x5A78A1,
//            0x4D5894,
//            0x494187,
//            0x50367A,
//            0x572C6E,
//            0x5C2361,
//            0x541B49,
//            0x47142F,
//            0x3B0E1A,
//            0x2E0909,
//            0x210D05,
//            0x140C02,
//            0x080601
//    };

    private int[] colors = new int[]{
            0x000000,
            0x010804,
            0x041511,
            0x06201E,
            0x0A2A2C,
            0x0F343B,
            0x143D4B,
            0x1A465E,
            0x224E74,
            0x30538E,
            0x4656A4,
            0x605AB2,
            0x7A5EB8,
            0x9363BB,
            0xAA69B9,
            0xBF71B4,
            0xCF7CAE,
            0xDA8AA8,
            0xE19AA7,
            0xE6AAAA,
            0xE9BAB1,
            0xEDC9BA,
            0xF1D7C7,
            0xF5E5D6
    };

    private boolean checkCardioidOrBulb(ComplexNumber c) {

        double cRe = c.getReal();
        double cIm = c.getImaginary();

        double cbSquared = cIm * cIm;
        double cResubQ = cRe - 0.25;
        double q = cResubQ * cResubQ + cbSquared;
        double a = q*q+q*cResubQ;
        double b = 0.25*cbSquared;

        double p2 = cRe * cRe + 2 * cRe + 1 + cbSquared;

        if (p2 <= 0.0625) {
            return true;
        }

        return a <= b;
//        Translated from LUA COde below.

//        --check if point in main cardiod
//        local cb2=cb*cb
//        local casubq=ca-0.25
//        local q=casubq*casubq+cb2
//        local a=q*q+q*casubq
//        local b=0.25*cb2
//
//                --check if point in period 2 bulb
//        local p2=ca*ca+2*ca+1+cb2
//
//        if p2 <= 0.0625 then
//                n=max_iterations
//        end
//
//        if a<=b then
//                n=max_iterations
//        end
    }


    public Mandelbrot(int width, int height, double centerX, double centerY, double zoom) {
        this.width = width;
        this.height = height;
        this.zoom = zoom;
        this.centerX = centerX;
        this.centerY = centerY;

        this.maxIterations = 300;
        this.cPlane = 1.5;
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

                // To add later: additional maths for quickly checking if point in main cardiod or bulb
                // https://loiseaujc.github.io/Scientific_Computing_on_a_Laptop/Maths/Mandelbrot/definition.html
                boolean cardioid = false;
                boolean bulb = false;

                int iterations = 0;

                if (checkCardioidOrBulb(c)) {
                    iterations = maxIterations;
                }

                while (iterations < maxIterations) {

                    double magnitude = z.getMagnitude();

                    if (magnitude > 4) {
                        break;
                    }

                    z = z.square();
                    z = z.add(c);

                    iterations++;
                }

                // map the number of iterations to array of colours stored above.
                // Note for future: Explore different colouring methods, perhaps add as an option.
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
