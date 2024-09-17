public class Polynomial {
    double[] coeff;
    public Polynomial() {
        this.coeff = new double[]{0};
    }
    public Polynomial(double[] arr) {
        this.coeff = arr;
    }
    public Polynomial add (Polynomial newpol){
        int sizeogpol = this.coeff.length;
        int sizenewpol = newpol.coeff.length;
        int max = Math.max(sizenewpol,sizeogpol);
        int i = 0;
        Polynomial temp = new Polynomial(newpol.coeff);
        if (sizenewpol <= sizeogpol){   //keep og Polynomial array
            while (i < max){
                if (i < sizenewpol && i < sizeogpol){
                    this.coeff[i] = this.coeff[i] + newpol.coeff[i];
                }
                i++;
            }
        }
        else{ // keep new poly array
            while (i < max){
                if (i < sizenewpol && i < sizeogpol){
                    temp.coeff[i] = this.coeff[i] + newpol.coeff[i];
                }
                i++;
            }
            this.coeff = temp.coeff;
        }
        return this;
    }

    public double evaluate(double x){
        double total = 0;
        for (int i = 0; i < this.coeff.length; i++){
            total = total + this.coeff[i]*Math.pow(x,i);
        }
        return total;
    }
    public boolean hasRoot(double x){
        return this.evaluate(x) == 0;
    }
}
