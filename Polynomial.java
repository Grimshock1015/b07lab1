import java.io.*;
import java.util.*;

public class Polynomial {
    double[] coeff;
    int[] exp;
    public Polynomial() {
        this.coeff = new double[]{0};
        this.exp = new int[]{0};
    }
    public Polynomial(double[] coeff, int[] exp) {
        this.coeff = coeff;
        this.exp = exp;
    }
    public Polynomial(File file) throws FileNotFoundException {
        HashMap<Integer,Double> terms = new HashMap<>();
        Scanner scanner = new Scanner(file);
        String poly = scanner.nextLine();
        scanner.close();
        String[] strArray = poly.split("(?=[+-])");
        for (String term : strArray){
            double coefficient;
            int power;
            if (term.contains("x")){
                String[] parts = term.split("x");
                if (parts[0].isEmpty() || parts[0].equals("+")) {
                    coefficient = 1;
                }
                else if (parts[0].equals("-")) {
                    coefficient = -1;
                }
                else {
                    coefficient = Double.parseDouble(parts[0]);
                }
                if (parts.length > 1 && !parts[1].isEmpty()) {
                    power = Integer.parseInt(parts[1].trim());
                }
                else {
                    power = 1;
                }
            }
            else {
                coefficient = Double.parseDouble(term);
                power = 0;
            }
            terms.put(power, terms.getOrDefault(power, 0.0) + coefficient);
        }
        maptopoly(this, terms);
    }
    public Polynomial add (Polynomial newpol){
        Polynomial ans = new Polynomial();
        HashMap<Integer,Double> temp = new HashMap<>();
        for (int i = 0; i < newpol.coeff.length; i++){
            if(temp.containsKey(newpol.exp[i])){
                temp.put(newpol.exp[i], temp.get(newpol.exp[i]) + newpol.coeff[i]);
            }
            else{
                temp.put(newpol.exp[i], newpol.coeff[i]);
            }
        }
        for (int i = 0; i < this.coeff.length; i++){
            if(temp.containsKey(this.exp[i])){
                temp.put(this.exp[i], temp.get(this.exp[i]) + this.coeff[i]);
            }
            else{
                temp.put(this.exp[i], this.coeff[i]);
            }
        }
        return maptopoly(ans, temp);
    }

    public double evaluate(double x){
        double total = 0;
        for (int i = 0; i < this.coeff.length; i++){
            total = total + this.coeff[i]*Math.pow(x,this.exp[i]);
        }
        return total;
    }
    public boolean hasRoot(double x){
        return this.evaluate(x) == 0;
    }

    public Polynomial multiply(Polynomial newpol){
        //Add a method named multiply that takes one argument of type Polynomial and returns
        //the polynomial resulting from multiplying the calling object and the argument. The
        //resulting polynomial should not contain redundant exponents.
        Polynomial ans = new Polynomial();
        HashMap<Integer,Double> temp = new HashMap<>();
        for(int i = 0; i < this.coeff.length; i++){
            for(int j = 0; j < newpol.coeff.length; j++){
                if(temp.containsKey(newpol.exp[j] + this.exp[i])){
                    temp.put(newpol.exp[j] + this.exp[i], temp.get(newpol.exp[j] + this.exp[i]) + newpol.coeff[j]*this.coeff[i]);
                }
                else{
                    temp.put(newpol.exp[j] + this.exp[i], newpol.coeff[j]*this.coeff[i]);
                }
            }
        }
        return maptopoly(ans, temp);
    }

    public Polynomial maptopoly(Polynomial ans, HashMap<Integer, Double> temp) {
        ans.coeff = new double[temp.size()];
        ans.exp = new int[temp.size()];
        List<Map.Entry<Integer, Double>> entries = new ArrayList<>(temp.entrySet());
        entries.removeIf(entry -> entry.getValue() == 0.0);

        // Sort the entries by exponent in ascending order
        entries.sort((a, b) -> Integer.compare(a.getKey(), b.getKey()));

        int index = 0;
        for (Map.Entry<Integer, Double> entry : entries) {
            ans.exp[index] = entry.getKey();
            ans.coeff[index] = entry.getValue();
            index++;
        }

        return ans;
    }

    public void saveToFile(String fileName) {
        // StringBuilder to construct the polynomial string
        StringBuilder newpoly = new StringBuilder();

        for (int i = 0; i < coeff.length; i++) {
            double coefficient = coeff[i];
            int exponent = exp[i];
            if (coefficient == 0) {
                continue;
            }

            if (newpoly.length() > 0) {
                newpoly.append(coefficient > 0 ? "+" : "");
            }

            // Append the coefficient
            if (exponent == 0) {
                newpoly.append(coefficient); // Constant term
            } else if (exponent == 1) {
                newpoly.append(coefficient == 1 ? "x" : coefficient == -1 ? "-x" : coefficient + "x");
            } else {
                newpoly.append(coefficient == 1 ? "x" + exponent : coefficient == -1 ? "-x" + exponent : coefficient + "x" + exponent);
            }
        }

        // Write the polynomial string to the specified file
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(newpoly.toString());
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception as needed
        }
    }
}