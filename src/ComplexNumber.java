public class ComplexNumber {

    private double real;
    private double imaginary;

    public ComplexNumber() {
        this.real = 0.0;
        this.imaginary = 0.0;
    }

    public void set(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public void setReal(double real) {
        this.real = real;
    }

    public void setImaginary(double imaginary) {
        this.imaginary = imaginary;
    }

    public ComplexNumber(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }



    public ComplexNumber add(ComplexNumber n) {
        double sumRe;
        double sumIm;

        sumRe = this.real + n.real;
        sumIm = this.imaginary + n.imaginary;

        return new ComplexNumber(sumRe, sumIm);
    }

    public ComplexNumber square() {
        return this.multiply(this);
    }

    public ComplexNumber multiply(ComplexNumber z) {
        double productRe;
        double productIm;

        productRe = (this.real * z.real) - (this.imaginary * z.imaginary);
        productIm = (this.real * z.imaginary) + (z.real * this.imaginary);

        return new ComplexNumber(productRe, productIm);
    }

    public double getMagnitude() {
        return this.real * this.real + this.imaginary * this.imaginary;
    }

    public double getReal() {
        return this.real;
    }

    public double getImaginary() {
        return this.imaginary;
    }

    @Override
    public String toString() {
        return "("+this.real+","+this.imaginary+"i)";
    }
}
