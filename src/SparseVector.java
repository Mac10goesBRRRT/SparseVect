public class SparseVector {
    Node head;
    private int length;
    public SparseVector() {
    }

    /**
     * Creates a SparseVector
     * @param length Dimension of the Vector. A Vector can not have more Dimensions added after Creation.
     */
    public SparseVector(int length){
        this.length = length;
    }

    /**
     * Sets the Value at the specified Index
     * @param index Where the value will be set.
     * @param value The Value to set. Will create a new node if the current Value is 0.
     * @throws IndexOutOfBoundsException When the Index is Negative or of a Higher Dimensional Vector
     */
    public void setElement(int index, double value) throws IndexOutOfBoundsException{
        boolean indexEx = indexExists(index);
        //Do not set anything if value is 0.0
        if(value == 0.0) {
            if (!indexEx)
                return;
            else {
                removeElement(index);
                return;
            }
        }
        //Check index
        if(index >= this.length || index < 0)
            throw new IndexOutOfBoundsException("Index of " + index + " is out of Bounds for length " + this.getLength());
        //Does the Element exist?
        //if the vector exists but does not have an index at 0, start a new vector and append the old
        if(index == 0 && head != null){
            Node new_head = new Node(value, index);
            new_head.setNext(head);
            this.head = new_head;
            return;
        }
        //if the vector is empty, start a new vector
        if(head == null) {
            this.head = new Node(value, index);
        } else if (indexEx) {
            Node entry = this.head;
            while(entry.index != index){
                entry = entry.next;
            }
            entry.value = value;
        } else if (!indexEx){
            Node entry = this.head;
            while(entry.next != null && entry.index < index){
                entry = entry.next;
            }
            Node new_node = new Node(value, index);
            if(entry.next!=null)
                new_node.setNext(entry.next);
            entry.setNext(new_node);
        }
        else {
            //Needs to be able to insert
            Node new_node = new Node(value, index);
            Node entry = this.head;
            while (entry.next != null) {
                entry = entry.next;
            }
            entry.setNext(new_node);
        }
    }

    /**
     *
     * @param index which Value to get.
     * @return Value at current Index
     * @throws IndexOutOfBoundsException When trying to Access a negative or to high Dimension
     */
    public double getElement(int index) throws IndexOutOfBoundsException{
        if(index >= this.length || index < 0){
            throw new IndexOutOfBoundsException();
        }
        if(index == 0){
            return this.head.value;
        } else {
            Node last = this.head.next;
            while(last.index != index) {
                last = last.next;
            }
            return last.value;
        }
    }

    /**
     * Removes the Element at index
     * @param index to remove. If the Dimension is higher or lower will not throw an error because the behaviour isnt wrong
     */
    public void removeElement(int index){
        Node pre = this.head;
        while(pre.next.index != index){
            pre = pre.next;
        }
        if(pre.next.next != null)
            pre.next = pre.next.next;
        else
            pre.next = null;
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
            throw new ArithmeticException("Can not add Vectors of different lengths");
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
    private boolean indexExists(int index) {
        Node to_check = this.head;
        while(to_check != null){
            if(index == to_check.index)
                return true;
            to_check = to_check.next;
        }
        return false;
    }

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