import org.junit.jupiter.api.Assertions;
import java.util.Arrays;


class CheckSparseVector {

    //Checking if Values can be set/get
    @org.junit.jupiter.api.Test
    void setAndGetElement() {
        SparseVector newV = new SparseVector(3);
        newV.setElement(0,1.0);
        Double nodeValue = newV.getElement(0);
        Assertions.assertEquals(1.0, nodeValue, "Set/Get Difference, expected: 1.0, actual: " + nodeValue);
    }

    //Adding in different Orders should still produce the same 2 Vectors
    @org.junit.jupiter.api.Test
    void SettingDifferentOrdersIsEqual() {
        SparseVector A = new SparseVector(12);
        //Setting A
        A.setElement(4, 4.0);
        A.setElement(1, 1.0);
        A.setElement(10, 2.0);
        A.setElement(11, 8.0);
        A.setElement(6, 2.0);
        //Setting B
        SparseVector B = new SparseVector(12);
        B.setElement(1, 1.0);
        B.setElement(4, 4.0);
        B.setElement(6, 2.0);
        B.setElement(10, 2.0);
        B.setElement(11, 8.0);
        Assertions.assertTrue(A.equals(B), "Vectors A and B are different");
    }

    //Setting the head Element, the rest of the Vector has to stay the same and must not be forgotten
    @org.junit.jupiter.api.Test
    void setIndex0() {
        SparseVector newV = new SparseVector(3);
        newV.setElement(1,1.0);
        newV.setElement(0,2.0);
        Double nodeValue = newV.getElement(0);
        Assertions.assertEquals(2.0, nodeValue, "Set/Get Difference, expected: 2.0, actual: " + nodeValue);
    }


    //Removing the head must make the second Element the new head
    @org.junit.jupiter.api.Test
    void removeElementAtHead() {
        SparseVector newV = new SparseVector(2);
        newV.setElement(0,1.0);
        newV.setElement(1,2.0);
        newV.removeElement(0);
        Double nodeValue = newV.getElement(0);
        Assertions.assertEquals(0.0, nodeValue, "removed wrong Element, expected: 0.0, actual: " + nodeValue);
    }

    //Removing at the Tail does not cause Exceptions
    @org.junit.jupiter.api.Test
    void removeElementAtTail() {
        SparseVector newV = new SparseVector(2);
        newV.setElement(0,1.0);
        newV.setElement(1,2.0);
        newV.removeElement(1);
        Double nodeValue = newV.getElement(1);
        Assertions.assertEquals(0.0, nodeValue, "removed wrong Element, expected: 0.0, actual: " + nodeValue);
    }

    //The Vector must be linked correctly after removing an element between head and Tail
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

    //The length should be returned
    @org.junit.jupiter.api.Test
    void getLength() {
        SparseVector newV = new SparseVector(10);
        int length = newV.getLength();
        Assertions.assertEquals(10, length, "set wrong length, expected: 10, actual: " + length);
    }

    //Two Vectors with the same length and same Elements should be the same
    @org.junit.jupiter.api.Test
    void testEqualsVectorsEqual() {
        double[] A = {1.0,2.0,3.0,4.0,5.0};
        SparseVector newA = new SparseVector(5).toSparseVector(A);
        SparseVector newB = new SparseVector(5).toSparseVector(A);
        boolean equal = newA.equals(newB);
        Assertions.assertTrue(equal, "Expected SparseVectors to be equal");
    }

    //Two Vectors with different Elements should be different
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

    //Adding while one Vector is empty should Change the "me" Vector
    @org.junit.jupiter.api.Test
    void addOtherVectIsEmpty() {
        double[] A = {1.0,2.0,3.0,4.0,5.0};
        SparseVector newA = new SparseVector(5).toSparseVector(A);
        SparseVector newB = new SparseVector(5);
        newA.add(newB);
        double[] Result = newA.toArray();
        Assertions.assertArrayEquals(A, Result, "Arrays Are different");
    }

    //Adding while one Vector is empty should Change the Me Vector. This time the "me" Vector is empty
    @org.junit.jupiter.api.Test
    void addMeVectIsEmpty() {
        double[] B = {1.0,2.0,3.0,4.0,5.0};
        SparseVector newA = new SparseVector(5);
        SparseVector newB = new SparseVector(5).toSparseVector(B);
        newA.add(newB);
        double[] Result = newA.toArray();
        Assertions.assertArrayEquals(B, Result, "Arrays Are different");
    }

    //Making sure the Reference to Other Vect stays the same and is not just copied into the original Vector
    @org.junit.jupiter.api.Test
    void addMeVectIsEmptyReferenceToOtherIsNotCopied() {
        double[] B = {1.0,2.0,3.0,4.0,5.0};
        SparseVector newA = new SparseVector(5);
        SparseVector newB = new SparseVector(5).toSparseVector(B);
        newA.add(newB);
        newB.setElement(0, 1.1);
        B[0] = 1.1;
        double[] Result = newB.toArray();
        Assertions.assertArrayEquals(B, Result, "Arrays Are different");
    }

    //Adding two Vectors leads to the Expected Results
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

    //Adding Vectors, where this indices are 0 after the second element and others start after the fith, to check if the references are correct
    @org.junit.jupiter.api.Test
    void add2VectsCheckNodes(){
        SparseVector A = new SparseVector(6);
        SparseVector B = new SparseVector(6);
        A.setElement(0,1.0);
        A.setElement(1,1.0);
        B.setElement(4, 4.0);
        B.setElement(5, 5.0);
        A.add(B);
        B.setElement(4, 4.4);
        double[] ResultA = A.toArray();
        double[] ExpectedA = {1.0, 1.0, 0.0, 0.0, 4.0, 5.0};
        double[] ResultB = B.toArray();
        double[] ExpectedB = {0.0, 0.0, 0.0, 0.0, 4.4, 5.0};
        Assertions.assertArrayEquals(ExpectedA, ResultA, "Vector A differs From expected Result after Addition");
        Assertions.assertArrayEquals(ExpectedB, ResultB, "Vector B differs From expected Result after Addition");
    }


    //Adding different Lengths leads to an error
    @org.junit.jupiter.api.Test
    void add2DifferentLengths(){
        SparseVector newA = new SparseVector(5);
        SparseVector newB = new SparseVector(4);
        Exception exception = Assertions.assertThrows(ArithmeticException.class, () -> newA.add(newB), "Unexpected Exception Thrown");
        String expectedMessage = "Can not add Vectors of length 5 and 4";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    //Out of Bounds Errors for Get, Set, Remove
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

    //Negative Vector size throws an Error because negative Dimensions should not exist
    @org.junit.jupiter.api.Test
    void NegativeVectorSize(){
        Exception exception = Assertions.assertThrows(NegativeArraySizeException.class, () -> new SparseVector(-1), "Unexpected Exception Thrown");
        String expectedMessage = "Vector cant have negative Dimensions";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }


}