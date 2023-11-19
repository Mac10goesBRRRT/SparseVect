import java.util.Arrays;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        int big_index = 300000000;
        double[] arr = {1.0,2.0,3.0,0.0,0.2,0.3,9.9};
        //TODO: X ist empty and Y is added on top.
        //TODO: X is empty after a certain index, Y is empty before said index
        //TODO: insert at wrong index
        double[] x = {1.0,-1.0,1.0};
        double[] y = {0.0,1.0,-1.0};
        SparseVector X = new SparseVector().toSparseVector(x);
        SparseVector Y = new SparseVector().toSparseVector(y);
        SparseVector Giant = new SparseVector(big_index+1);
        Giant.setElement(0,10.0);
        Giant.setElement(big_index, 10.0);
        double[] hurr = Giant.toArray();
        X.add(Y);
        X.setElement(5, 0.1);
        //X.setElement(0,0.1);
        //System.out.println("Vector X == Y: " + X.equals(Y));
        System.out.println("Vector X + Y: " + Arrays.toString(X.toArray()));
        /*
        SparseVector A = new SparseVector().toSparseVector(arr);
        SparseVector V = new SparseVector(10);
        System.out.println(V.getLength());
        V.setElement(1,1.0);
        V.setElement(2,2.0);
        V.setElement(3,3.0);
        A.setElement(1,-4.5);
        System.out.println(V.getElement(2));
        V.removeElement(3);
        System.out.println(V.getElement(3));
        System.out.println("vector V: "+ Arrays.toString(V.toArray()));
        System.out.print("vector A: " + Arrays.toString(A.toArray()));
        //System.out.println(V.getElement(0));
        //System.out.println(V.getElement(1));
        //System.out.println(V.getElement(2));
        //System.out.println(V.getElement(4));
        */
    }
}
