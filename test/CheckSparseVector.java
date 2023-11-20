import org.junit.jupiter.api.Assertions;
import java.util.Arrays;


class SparseVectorTest {

    @org.junit.jupiter.api.Test
    void setAndGetElement() {
        SparseVector newV = new SparseVector(3);
        newV.setElement(0,1.0);
        Double nodeValue = newV.getElement(0);
        Assertions.assertEquals(1.0, nodeValue, "Set/Get Difference, expected: 1.0, actual: " + nodeValue);
    }

    @org.junit.jupiter.api.Test
    void setIndex0() {
        SparseVector newV = new SparseVector(3);
        newV.setElement(1,1.0);
        newV.setElement(0,2.0);
        Double nodeValue = newV.getElement(0);
        Assertions.assertEquals(2.0, nodeValue, "Set/Get Difference, expected: 2.0, actual: " + nodeValue);
    }


    @org.junit.jupiter.api.Test
    void removeElementAtHead() {
        SparseVector newV = new SparseVector(2);
        newV.setElement(0,1.0);
        newV.setElement(1,2.0);
        newV.removeElement(0);
        Double nodeValue = newV.getElement(0);
        Assertions.assertEquals(0.0, nodeValue, "removed wrong Element, expected: 0.0, actual: " + nodeValue);
    }

    @org.junit.jupiter.api.Test
    void removeElementAtTail() {
        SparseVector newV = new SparseVector(2);
        newV.setElement(0,1.0);
        newV.setElement(1,2.0);
        newV.removeElement(1);
        Double nodeValue = newV.getElement(1);
        Assertions.assertEquals(0.0, nodeValue, "removed wrong Element, expected: 0.0, actual: " + nodeValue);
    }

    @org.junit.jupiter.api.Test
    void removeSecondElement() {
        SparseVector newV = new SparseVector(3);
        newV.setElement(0,1.0);
        newV.setElement(1,2.0);
        newV.setElement(2,3.0);
        newV.removeElement(1);
        Double nodeValue = newV.getElement(1);
        Assertions.assertEquals(0.0, nodeValue, "removed wrong Element, expected: 0.0, actual: " + nodeValue);
        nodeValue = newV.getElement(2);
        Assertions.assertEquals(3.0, nodeValue, "removed wrong Element, expected: 3.0, actual: " + nodeValue);
    }

    @org.junit.jupiter.api.Test
    void getLength() {
        SparseVector newV = new SparseVector(10);
        int length = newV.getLength();
        Assertions.assertEquals(10, length, "set wrong length, expected: 10, actual: " + length);
    }

    @org.junit.jupiter.api.Test
    void testEqualsVectorsEqual() {
        double[] A = {1.0,2.0,3.0,4.0,5.0};
        SparseVector newA = new SparseVector(5).toSparseVector(A);
        SparseVector newB = new SparseVector(5).toSparseVector(A);
        boolean equal = newA.equals(newB);
        Assertions.assertTrue(equal, "Expected SparseVectors to be equal");
    }

    @org.junit.jupiter.api.Test
    void testEqualsVectorsValuesNotEqual() {
        double[] A = {1.0,2.0,3.0,4.0,5.0};
        double[] B = {0.0,1.0,2.0,3.0,4.0};
        SparseVector newA = new SparseVector(5).toSparseVector(A);
        SparseVector newB = new SparseVector(5).toSparseVector(B);
        boolean equal = newA.equals(newB);
        Assertions.assertFalse(equal, "Expected SparseVectors to be different");
    }

    @org.junit.jupiter.api.Test
    void testEqualsVectorsLengthNotEqual() {
        SparseVector newA = new SparseVector(1);
        SparseVector newB = new SparseVector(2);
        boolean equal = newA.equals(newB);
        Assertions.assertFalse(equal, "Expected SparseVectors to be different");
    }

    @org.junit.jupiter.api.Test
    void addOtherVectIsEmpty() {
        double[] A = {1.0,2.0,3.0,4.0,5.0};
        SparseVector newA = new SparseVector(5).toSparseVector(A);
        SparseVector newB = new SparseVector(5);
        newA.add(newB);
        double[] Result = newA.toArray();
        Assertions.assertArrayEquals(A, Result, "Arrays Are different");
    }

    @org.junit.jupiter.api.Test
    void addMeVectIsEmpty() {
        double[] B = {1.0,2.0,3.0,4.0,5.0};
        SparseVector newA = new SparseVector(5);
        SparseVector newB = new SparseVector(5).toSparseVector(B);
        newA.add(newB);
        double[] Result = newA.toArray();
        Assertions.assertArrayEquals(B, Result, "Arrays Are different");
    }

    @org.junit.jupiter.api.Test
    void add2Vects(){
        double[] A = {1.0,2.0,-3.0,-4.0,5.0};
        double[] B = {1.0,-2.0,2.0,4.0,-4.0};
        double[] ExpectedResult = {2.0, 0.0, -1.0, 0.0, 1.0};
        SparseVector newA = new SparseVector(5).toSparseVector(A);
        SparseVector newB = new SparseVector(5).toSparseVector(B);
        newA.add(newB);
        double[] Result = newA.toArray();
        Assertions.assertArrayEquals(ExpectedResult, Result,"Arrays Are different Expected: " + Arrays.toString(ExpectedResult) + " Actual: " + Arrays.toString(Result));
    }

    @org.junit.jupiter.api.Test
    void add2DifferentLengths(){
        SparseVector newA = new SparseVector(5);
        SparseVector newB = new SparseVector(4);
        Exception exception = Assertions.assertThrows(ArithmeticException.class, () -> newA.add(newB), "Unexpected Exception Thrown");
        String expectedMessage = "Can not add Vectors of length 5 and 4";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @org.junit.jupiter.api.Test
    void AccessIndexOutOfBounds_getElement(){
        SparseVector newA = new SparseVector(1);
        Exception exception = Assertions.assertThrows(IndexOutOfBoundsException.class, () -> newA.getElement(2), "Unexpected Exception Thrown");
        String expectedMessage = "Index of 2 is out of Bounds for length 1";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @org.junit.jupiter.api.Test
    void AccessIndexOutOfBounds_setElement(){
        SparseVector newA = new SparseVector(1);
        Exception exception = Assertions.assertThrows(IndexOutOfBoundsException.class, () -> newA.setElement(2, 1.0), "Unexpected Exception Thrown");
        String expectedMessage = "Index of 2 is out of Bounds for length 1";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @org.junit.jupiter.api.Test
    void AccessIndexOutOfBounds_removeElement(){
        SparseVector newA = new SparseVector(1);
        Exception exception = Assertions.assertThrows(IndexOutOfBoundsException.class, () -> newA.removeElement(2), "Unexpected Exception Thrown");
        String expectedMessage = "Index of 2 is out of Bounds for length 1";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

}