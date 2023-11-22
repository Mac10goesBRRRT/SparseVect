public class SparseVector {
    Node head;
    private int length;
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
            removeElement(index);
            return;
        }
        //Create new Vector
        if(head == null) {
            this.head = new Node(value, index);
            return;
        }
        //Create new head
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
            //The index does not exists
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
        while(curr != null && curr.index != index){
            curr = curr.next;
        }
        if(curr != null)
            return curr.value;
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
            return;
        while (curr != null && curr.index < index) {
            prev = curr;
            curr = curr.next;
        }
        if (this.head == curr) {
            this.head = curr.next;
            return;
        }
        //Ignore the IntelliJ error, this does not happen.
        if(curr.next != null){
            prev.setNext(curr.next);
        } else {
            prev.next = null;
        }
    }

    /**
     * Dimension of the Vector
     * @return Integer
     */
    public int getLength(){
        return this.length;
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
        if(me != null ^ not_me != null) //(me != null && not_me == null) || (me == null && not_me != null)
            return false;
        while(me != null){
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
            this.head = other.head;
            return;
        }
        if(not_me == null)
            return;
        while(me != null && not_me != null){
            if(me.index == not_me.index) {
                this.setElement(me.index, me.value + not_me.value);
                me = me.next;
                not_me = not_me.next;
            } else if (me.index > not_me.index){
                this.setElement(not_me.index, not_me.value);
                not_me = not_me.next;
            } else {
                if(me.next == null) {
                    me.setNext(not_me);
                    break;
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
        for(int i = 0; i < array.length; i++){
            a.setElement(i, array[i]);
        }
        return a;
    }
}

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