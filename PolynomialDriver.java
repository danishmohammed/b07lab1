import java.io.File;
import java.io.IOException;

public class PolynomialDriver {
    public static void main(String[] args) throws IOException {
    
        // Create polynomials
        double[] coef1 = {3, -2, 1}; // 3x^2 - 2x + 1
        int[] exp1 = {2, 1, 0};
        Polynomial poly1 = new Polynomial(coef1, exp1);

        double[] coef2 = {-1, 4}; // -x + 4
        int[] exp2 = {1, 0};
        Polynomial poly2 = new Polynomial(coef2, exp2);
        
        // Add polynomials
        Polynomial sum = poly1.add(poly2);
        System.out.println("Sum of polynomials: "); // Expected: 3x^2 - 3x + 5
        sum.printC();
        System.out.println();
        sum.printX();
        System.out.println();

        // Evaluate polynomial at x = 2
        double x = 2;
        double result = poly1.evaluate(x);
        System.out.println("Evaluation of polynomial 1 at x = " + x + ": " + result); // Expected: 3

        // Check if polynomial has a root at x = 1
        boolean hasRoot = poly1.hasRoot(1);
        System.out.println("Polynomial 1 has a root at x = 1: " + hasRoot); // Expected: false

        // Multiply polynomials
        Polynomial product = poly1.multiply(poly2);
        System.out.println("Product of polynomials: "); // Expected: -3x^3 + 14x^2 - 9x + 4
        product.printC();
        System.out.println();
        product.printX();
        System.out.println();

        // Save polynomial to file
        poly1.saveToFile("polynomial.txt");
        System.out.println("Polynomial saved to file.");

        // Create polynomial from file
        File f = new File("polynomial.txt");
        Polynomial polyFromFile = new Polynomial(f);
        System.out.println("Polynomial read from file: "); // Expected: 3x^2 - 2x + 1
        polyFromFile.printC();
        System.out.println();
        polyFromFile.printX();
        System.out.println();
    }
}