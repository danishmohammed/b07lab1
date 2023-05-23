public class Polynomial {

    double[] coefficients;

    public Polynomial() {
        coefficients = new double[1];
        coefficients[0] = 0;
    }

    public Polynomial(double[] array) {
        coefficients = array;
    }

    public Polynomial add(Polynomial poly2) {
        int first = coefficients.length;
        int second = poly2.coefficients.length;
        int size = Math.max(first, second);
        double[] sum = new double[size];

        int i = 0;
        while (i < first && i < second) {
            sum[i] = coefficients[i] + poly2.coefficients[i];
            i++;
        }
        if (first > second) {
            while (i < first) {
                sum[i] = coefficients[i];
                i++;
            }
        } else if (second > first) {
            while (i < second) {
                sum[i] = poly2.coefficients[i];
                i++;
            }
        }
        Polynomial p = new Polynomial(sum);
        return p;
    }

    public double evaluate(double x) {
        int size = coefficients.length;
        double sum = 0;
        for (int i = 0; i < size; i++) {
            sum += coefficients[i] * Math.pow(x, i);
        }
        return sum;
    }

    public boolean hasRoot(double x) {
        return (evaluate(x) == 0);
    }

}