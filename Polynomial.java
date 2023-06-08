import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Polynomial {

    double[] coefficients;
    int[] x_exponents;

    public Polynomial() {
        coefficients = null;
        x_exponents = null;
    }

    public Polynomial(double[] coefficients, int[] x_exponents) {
        this.coefficients = coefficients;
        this.x_exponents = x_exponents;
    }

    public Polynomial(File f) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(f));
        
        String line = br.readLine();
        if (line.equals("")) {
            coefficients = null;
            x_exponents = null;
        } else {
            line = line.replace("-", "~-");
            line = line.replace("+", "~+");
            String[] terms = line.split("~");
            int i = 0;
            if (terms[0].equals("")) {
                i = 1;
            }

            Polynomial p = new Polynomial(new double[]{1}, new int[]{0});
            for (;i < terms.length; i++) {
                String current = terms[i];
                int indx = current.indexOf("x");
                int x_exp;
                double co;
                if (indx == -1) {
                    x_exp = 0;
                } else if (current.substring(indx+1).equals("")) {
                    x_exp = 1;
                } else {
                    
                    x_exp = Integer.parseInt(current.substring(indx+1));
                }
                if (indx == -1) {
                    co = Double.parseDouble(current);
                } else {
                    co = Double.parseDouble(current.substring(0,indx));
                }

                p = p.add(new Polynomial(new double[]{co}, new int[]{x_exp}));
                
            }
            p = p.add(new Polynomial(new double[]{-1}, new int[]{0}));
            coefficients = p.coefficients;
            x_exponents = p.x_exponents;
        }
        br.close();
    }

    public Polynomial add(Polynomial poly2) {
        if (coefficients == null || poly2.coefficients == null) {
            System.out.println("Error, polynomial is not initialized add");
            return new Polynomial();          
        }
        int size1 = x_exponents.length;
        int size2 = poly2.x_exponents.length;

        for (int i = 0; i < size1; i++) {
            for (int j = 0; j < size1 - 1 - i; j++) {
                if (x_exponents[j] > x_exponents[j+1]) {
                    int temp = x_exponents[j];
                    double temp2 = coefficients[j];
                    x_exponents[j] = x_exponents[j+1];
                    coefficients[j] = coefficients[j+1];
                    x_exponents[j+1] = temp;
                    coefficients[j+1] = temp2;
                }
            }
        }
        for (int i = 0; i < size2; i++) {
            for (int j = 0; j < size2 - 1 - i; j++) {
                if (poly2.x_exponents[j] > poly2.x_exponents[j+1]) {
                    int temp = poly2.x_exponents[j];
                    double temp2 = poly2.coefficients[j];
                    poly2.x_exponents[j] = poly2.x_exponents[j+1];
                    poly2.coefficients[j] = poly2.coefficients[j+1];
                    poly2.x_exponents[j+1] = temp;
                    poly2.coefficients[j+1] = temp2;
                }
            }
        }

        int first = coefficients.length;
        int second = poly2.coefficients.length;
        int i = 0;
        int j = 0;
        int size = 0;
        while (i < first && j < second) {
            if (x_exponents[i] == poly2.x_exponents[j]) {
                if (coefficients[i] + poly2.coefficients[j] != 0) {
                    size++;
                }
                i++;
                j++;
            } else if (x_exponents[i] < poly2.x_exponents[j]) {
                size++;
                i++;
            } else {
                size++;
                j++;
            }
        }
        size = size + first + second - i - j;
        double[] coef = new double[size];
        int[] new_x = new int[size];

        i = 0; j = 0;
        int index = 0;
        while (i < first && j < second) {
            if (x_exponents[i] == poly2.x_exponents[j]) {
                if (coefficients[i] + poly2.coefficients[j] != 0) {
                    new_x[index] = x_exponents[i];
                    coef[index] = coefficients[i] + poly2.coefficients[j];
                    index++;
                }
                i++;
                j++;
            } else if (x_exponents[i] < poly2.x_exponents[j]) {
                new_x[index] = x_exponents[i];
                coef[index] = coefficients[i];
                i++;
                index++;
            } else {
                new_x[index] = poly2.x_exponents[j];
                coef[index] = poly2.coefficients[j];
                j++;
                index++;
            }
        }

        if (index < size && first == i) {
            for (; index < size; index++, j++) {
                new_x[index] = poly2.x_exponents[j];
                coef[index] = poly2.coefficients[j];
            }
        } else if (index < size && second == j) {
            for (; index < size; index++, i++) {
                new_x[index] = x_exponents[i];
                coef[index] = coefficients[i];
            }
        }

        Polynomial p = new Polynomial(coef, new_x);
        return p;
    }

    public double evaluate(double x) {
        if (coefficients == null) {
            System.out.println("Error, polynomial is not initialized evaluate");
            return -10000000;          
        }
        int size = coefficients.length;
        double sum = 0;
        for (int i = 0; i < size; i++) {
            sum += coefficients[i] * Math.pow(x, x_exponents[i]);
        }
        return sum;
    }

    public boolean hasRoot(double x) {
        if (coefficients == null) {
            System.out.println("Error, polynomial is not initialized hasRoot");
            return false;
        }
        return (evaluate(x) == 0);
    }
    
    public Polynomial multiply(Polynomial poly2) {
        if (coefficients == null || poly2.coefficients == null) {
            System.out.println("Error, either polynomial is not initialized multiply");
            return new Polynomial();          
        }
        Polynomial n = new Polynomial(new double[]{1}, new int[]{0});

        for (int i = 0; i < coefficients.length; i++) {
            for (int j = 0; j < poly2.coefficients.length; j++) {
                //tempcoef[index] = coefficients[i] * poly2.coefficients[j];
                //tempx[index] = x_exponents[i] + poly2.x_exponents[j];
                double[] t = new double[]{coefficients[i] * poly2.coefficients[j]};
                int[] u = new int[]{x_exponents[i] + poly2.x_exponents[j]};
                n = n.add(new Polynomial(t, u));
            }
        }
        n = n.add(new Polynomial(new double[]{-1}, new int[]{0}));
        return n;
    }

    public void saveToFile(String name) throws IOException {
        File f = new File(name);
        if (f.exists() == false) {
            f.createNewFile();
        }
        FileWriter o = new FileWriter(name);
        int i = 0;
        
        o.write(Double.toString(coefficients[i]));
        if (x_exponents[i] != 0) {
            o.write("x");
            o.write(Integer.toString(x_exponents[i]));
        }
        for (i = 1; i < coefficients.length; i++) {
            if (coefficients[i] > 0) {
                o.write("+");
            }
            o.write(Double.toString(coefficients[i]));

            if (x_exponents[i] != 0) {
                o.write("x");
                o.write(Integer.toString(x_exponents[i])); 
            }
        }
        o.close();

    }
    public void printC() {
        if (coefficients == null) return;
        for (int i = 0 ; i < coefficients.length; i++) {
            System.out.print(coefficients[i] + " ");
        }
    }
    public void printX() {
        if (x_exponents == null) return;
        for (int i = 0 ; i < x_exponents.length; i++) {
            System.out.print(x_exponents[i] + " ");
        }
    }

}