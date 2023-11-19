import java.util.Arrays;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        //TODO: X ist empty and Y is added on top.
        //TODO: X is empty after a certain index, Y is empty before said index
        //TODO: insert at wrong index
        double[] x = {1.0,-1.0,1.0, 1.0};
        double[] y = {0.0,1.0,-1.0, 1.0};
        SparseVector X = new SparseVector().toSparseVector(x);
        SparseVector Y = new SparseVector().toSparseVector(y);
        SparseVector Z = new SparseVector(2);
        Z.setElement(0,1.0);
        Z.setElement(1,2.0);
        X.add(Y);
        System.out.println(X.getElement(0));
        //Z.removeElement(0);
        Z.removeElement(1);
        X.removeElement(3);
        X.setElement(3,4.0);
        X.setElement(0, 0.1);
        //X.setElement(0,0.1);
        System.out.println("Vector X == Y: " + X.equals(Y));
        System.out.println("Vector X + Y: " + Arrays.toString(X.toArray()));
    }
}
