/**
 * SparseVector implements a so called Sparse Vector. This type of Vector is a lot more space efficient
 * than a normal Vector in certain cases because only non-zero values are saved. For example a 300 Million - Dimensional
 * Vector with 2 non-zero Values is about 2.4GB, while the same SparseVector is about 88B.
 * To Transfer between Vector and SparseVector use the {@link #toArray()} and {@link #toSparseVector(double[])} methods.
 *
 * Use {@link #setElement(int, double)} to set Values, {@link #getElement(int)} to get values and {@link #removeElement(int)} to remove Values.
 * for comparing use {@link #equals(SparseVector)} and for adding {@link #add(SparseVector)}
 */
public class SparseVector {
    Node head;
    private int length;

    // 0-Dimensional Vector does not make sense, however the Standard constructor does exactly that.
    public SparseVector() {
        this.length = 0;
    }

    /**
     * Creates a SparseVector
     * @param length Dimension of the Vector. A Vector can not have more Dimensions added after Creation.
     */
    public SparseVector(int length) throws NegativeArraySizeException{
        if(length < 0)
            throw new NegativeArraySizeException("Vector cant have negative Dimensions");
        this.length = length;
    }

    /**
     * Sets the Value at the specified Index
     * @param index Where the value will be set.
     * @param value The Value to set. Will create a new node if the current Value is 0.
     * @throws IndexOutOfBoundsException When the Index is Negative or of a Higher Dimensional Vector
     */
    public void setElement(int index, double value) throws IndexOutOfBoundsException{
        //Check if Index is in Bounds
        if(index < 0 || index >= this.getLength())
            throw new IndexOutOfBoundsException("Index of " + index + " is out of Bounds for length " + this.getLength());
        //Do not set anything if value is 0.0
        if(value == 0.0) {
            //Removing non-existing nodes is not a problem, so we'll just remove 0 values in all cases
            removeElement(index);
            return;
        }
        //Create new Vector, if none exists
        if(head == null) {
            this.head = new Node(value, index);
            return;
        }
        //Create new head, if an Element before the first Element needs to be added.
        if(index == 0 && head.index != 0){
            Node new_head = new Node(value, index);
            new_head.setNext(head);
            this.head = new_head;
            return;
        }
        //Create entry and prev for performance improvements
        Node entry = this.head;
        Node prev = null;
        //Go to the specified Index
        while(entry != null && entry.index < index){
            prev = entry;
            entry = entry.next;
        }
        //Were at the index already
        if(entry != null && entry.index == index){
            entry.value = value;
        } else {
            //The index does not exist
            Node new_node = new Node(value, index);
            if(prev != null){
                new_node.setNext(entry);
                prev.setNext(new_node);
            } else {
                new_node.setNext(head);
                this.head = new_node;
            }
        }
    }

    /**
     *
     * @param index which Value to get. Will return 0.0 if the index is within the Vector but no node exists
     * @return Value at current Index
     * @throws IndexOutOfBoundsException When trying to Access a negative or higher Dimension
     */
    public double getElement(int index) throws IndexOutOfBoundsException{
        if(index >= this.length || index < 0){
            throw new IndexOutOfBoundsException("Index of " + index + " is out of Bounds for length " + this.getLength());
        }
        Node curr = this.head;
        //run until the index is hit
        while(curr != null && curr.index != index){
            curr = curr.next;
        }
        //if there is something, return it
        if(curr != null)
            return curr.value;
        //if there is nothing, the Value is 0.0, since there was no node created.
        return 0.0;
    }

    /**
     * Removes the Element at index
     * @param index to remove. If the Element is already 0.0 at the specified index, removing it is not wrong, so no error is thrown
     * @throws IndexOutOfBoundsException When trying to Access a negative or higher Dimension
     */
    public void removeElement(int index) throws IndexOutOfBoundsException {
        if(index >= this.length || index < 0){
            throw new IndexOutOfBoundsException("Index of " + index + " is out of Bounds for length " + this.getLength());
        }
        Node curr = this.head;
        Node prev = null;
        if (curr == null)
            //if there is nothing to remove, we just go back
            return;
        while (curr != null && curr.index < index) {
            //run until the index is hit
            prev = curr;
            curr = curr.next;
        }
        if (this.head == curr) {
            //if we are still at the head, we need to remove the head and keep the rest
            this.head = curr.next;
            return;
        }
        //Ignore the IntelliJ error, this does not happen.
        if(curr.next != null){
            //if there is something behind the Element to be removed, we want to keep the reference
            prev.setNext(curr.next);
        } else {
            prev.next = null;
        }
    }

    /**
     * Dimension of the Vector
     * @return Integer of the Dimension
     */
    public int getLength(){
        //returns the Dimension (heres where the returning happens)
        return this.length;
        //Here is where Java will Pop the top of the Stack and Return to the Previous Element
    }

    /**
     * Compares to SparseVectors
     * @param other the other SparseVector to compare with
     * @return Boolean
     */
    public boolean equals(SparseVector other){
        if(this.getLength() != other.getLength())
            return false;
        Node me = head;
        Node not_me = other.head;
        //if Either is null while the other isnt, no need to check further because they wont be equal
        if(me != null ^ not_me != null) //(me != null && not_me == null) || (me == null && not_me != null)
            return false;
        while(me != null){
            //Run the Vectors, if either Value or Index are different, the Vectors are different
            if(me.value != not_me.value || me.index != not_me.index)
                return false;
            me = me.next;
            not_me = not_me.next;
        }
        return true;
    }

    /**
     * Adds to SparseVectors together
     * @param other SparseVector that is to be added
     * @throws ArithmeticException if Vectors are of different Dimensions
     */
    public void add(SparseVector other) throws ArithmeticException {
        if(this.getLength() != other.getLength())
            throw new ArithmeticException("Can not add Vectors of length " + this.getLength() + " and " + other.getLength());
        Node me = head;
        Node not_me = other.head;
        if(me == null){
            //if this Vector is 0, just copy the other Vector
            //Copying is needed as else both would have the same Reference
            while(not_me != null){
                this.setElement(not_me.index, not_me.value);
                not_me = not_me.next;
            }
            return;
        }
        if(not_me == null)
            //The other Vector is 0 and therefore nothing needs to be added
            return;
        while(me != null && not_me != null){
            if(me.index == not_me.index) {
                //If indices are equal we can just add
                //Values adding up to 0.0 are checked in the setElement method.
                this.setElement(me.index, me.value + not_me.value);
                me = me.next;
                not_me = not_me.next;
            } else if (me.index > not_me.index){
                //if me.index is larger than the other all elements between must be added.
                this.setElement(not_me.index, not_me.value);
                not_me = not_me.next;
            } else {
                //if me is at the end, we need new Nodes till the end of the Other Vector is reached
                if(me.next == null) {
                    Node extra = new Node(not_me.value, not_me.index);
                    me.setNext(extra);
                    not_me = not_me.next;
                    //break;
                }
                me = me.next;
            }
        }

    }


    //Useful other stuff
    /**
     * Converts a SparseVector to an array
     * @return Double[] Array
     */
    public double[] toArray(){
        double[] array = new double[this.length];
        Node curr = this.head;
        //Iterate all Elements
        while(curr != null){
            array[curr.index]=curr.value;
            curr = curr.next;
        }
        return array;
    }

    /**
     * Takes an Array and converts to SparseVector
     * @param array Double Array to convert
     * @return SparseVector representation of array
     */
    public SparseVector toSparseVector(double[] array){
        SparseVector a = new SparseVector(array.length);
        //Run the whole Array, Values of 0 can be read but will not be set because they are checked in the
        //setElement method
        for(int i = 0; i < array.length; i++){
            a.setElement(i, array[i]);
        }
        return a;
    }
}

//Helper Class for the Nodes. Similar to a Linked List with the addition of an Index
class Node {
    double value;
    int index;
    Node next;
    public Node(double value, int index) {
        this.index = index;
        this.value = value;
    }

    void setNext(Node next){
        this.next = next;
    }
}