import java.io.File;
import java.io.FileNotFoundException;

public class Driver {
	public static void main(String [] args) throws FileNotFoundException {
		Polynomial p = new Polynomial();
		System.out.println(p.evaluate(3));
		double [] c1 = {7,1,1,3,5};
		int[] c2 = {0,1,2,5,6};
		Polynomial p1 = new Polynomial(c1,c2);
		double [] a1 = {1,2,3,4};
		int[] a2 = {0,1,2,3};
		Polynomial p2 = new Polynomial(a1,a2);
		Polynomial s = p1.add(p2);
		s.saveToFile("test.txt");
		Polynomial s2 = p1.multiply(p2);
		s2.saveToFile("test2.txt");
		File test_file = new File("C:\\Users\\user\\IdeaProjects\\leetcode\\file_poly.txt");
		Polynomial s3 = new Polynomial(test_file);
		s3.saveToFile("test3.txt");
		System.out.println("s(1) = " + s.evaluate(1));
		if(s.hasRoot(1)){
			System.out.println("1 is a root of s");}
		else{
			System.out.println("1 is not a root of s");}
	}
}