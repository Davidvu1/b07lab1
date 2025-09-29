import java.io.*;

public class Polynomial {

    private double[] coefficients; // MAKE SURE THERE ARE NON ZERO STUFF
    private int[] exponents;

    public Polynomial() {
        this.coefficients = new double[0];
        this.exponents = new int[0];
    }

    public Polynomial(double[] coefficients, int[] exponents) {
        this.coefficients = coefficients;
        this.exponents = exponents;
    }

    public Polynomial(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = br.readLine();
        br.close();

        line = line.replace("-", "+-");
        String[] terms = line.split("\\+");

        int count = 0;
        for (String t : terms) {
            if (!t.isEmpty()) count++;
        }

        this.coefficients = new double[count];
        this.exponents = new int[count];

        int index = 0;
        for (String term : terms) {
            if (term.isEmpty()) continue;

            double c;
            int e;

            if (term.contains("x")) {
                String[] parts = term.split("x");

                // coefficients
                if (parts[0].isEmpty() || parts[0].equals("+")) c = 1;
                else if (parts[0].equals("-")) c = -1;
                else c = Double.parseDouble(parts[0]);

                // exponents
                if (parts.length == 1 || parts[1].isEmpty()) e = 1;
                else e = Integer.parseInt(parts[1]);
            } else {
                c = Double.parseDouble(term);
                e = 0;
            }

            this.coefficients[index] = c;
            this.exponents[index] = e;
            index++;
        }
    }

    public Polynomial add(Polynomial p) {

        double[] tempC = new double[this.coefficients.length + p.coefficients.length];
        int[] tempE = new int[this.exponents.length + p.exponents.length];
        int index = 0;

        for (int i = 0; i < this.coefficients.length; i++) {
            tempC[index] = this.coefficients[i];
            tempE[index] = this.exponents[i];
            index++;
        }

        for (int i = 0; i < p.coefficients.length; i++) {
            boolean found = false;
            for (int j = 0; j < index; j++) {
                if (tempE[j] == p.exponents[i]) {
                    tempC[j] += p.coefficients[i];
                    found = true;
                    break;
                }
            }
            if (!found) {
                tempC[index] = p.coefficients[i];
                tempE[index] = p.exponents[i];
                index++;
            }
        }

        double[] resultCoeff = new double[index];
        int[] resultExp = new int[index];
        for (int i = 0; i < index; i++) {
            resultCoeff[i] = tempC[i];
            resultExp[i] = tempE[i];
        }

        return new Polynomial(resultCoeff, resultExp);
    }

    public double evaluate(double x) {
        double total = 0.0;
        for (int i = 0; i < this.coefficients.length; i++) {
            total += this.coefficients[i] * Math.pow(x, this.exponents[i]);
        }
        return total;
    }

    public Polynomial multiply(Polynomial p) {
        double[] tempC = new double[this.coefficients.length * p.coefficients.length];
        int[] tempE = new int[this.coefficients.length * p.coefficients.length];
        int index = 0;

        for (int i = 0; i < this.coefficients.length; i++) {
            for (int j = 0; j < p.coefficients.length; j++) {
                double c = this.coefficients[i] * p.coefficients[j];
                int exp = this.exponents[i] + p.exponents[j];

                // Check if exp already exists
                boolean found = false;
                for (int k = 0; k < index; k++) {
                    if (tempE[k] == exp) {
                        tempC[k] += c;
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    tempC[index] = c;
                    tempE[index] = exp;
                    index++;
                }
            }
        }

        double[] resultCoeff = new double[index];
        int[] resultExp = new int[index];
        for (int i = 0; i < index; i++) {
            resultCoeff[i] = tempC[i];
            resultExp[i] = tempE[i];
        }

        return new Polynomial(resultCoeff, resultExp);
    }

    public boolean hasRoot(double x) {
        return this.evaluate(x) == 0;
    }

    public void saveToFile(String filename) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
        bw.write(this.toString());
        bw.close();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < coefficients.length; i++) {
            double c = coefficients[i];
            int e = exponents[i];

            if (c >= 0 && i > 0) sb.append("+");
            sb.append(c);
            if (e != 0) sb.append("x").append(e);
        }
        return sb.toString();
    }
}
