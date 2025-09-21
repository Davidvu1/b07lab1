

public class Polynomial {

    private double[] coefficients;

    public Polynomial() {

        this.coefficients = new double[1];
    }

    public Polynomial(double[] coefficients) {

        this.coefficients = coefficients;

    }

    public Polynomial add(Polynomial p) {

        for (int i = 0; i < this.coefficients.length; i++) {
            p.coefficients[i]= this.coefficients[i] + p.coefficients[i];
        }

        return p;
    }

    public double evaluate(double x) {

        double total = 0.0;

        for (int i = 0; i < this.coefficients.length; i++ ) {

            total += this.coefficients[i] * Math.pow(x, i);
        }

        return total;

    }

    public boolean hasRoot(double x) {

        return this.evaluate(x) == 0;
    }


}