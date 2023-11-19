public class SparseVector {
    Node head;
    int length;
    public SparseVector() {
    }
    public SparseVector(int length){
        this.length = length;
    }
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
    public int getLength(){
        return this.length;
    }
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
    public double[] toArray(){
        double[] array = new double[this.length];
        Node curr = this.head;
        while(curr != null){
            array[curr.index]=curr.value;
            curr = curr.next;
        }
        return array;
    }

    public SparseVector toSparseVector(double[] array){
        SparseVector a = new SparseVector(array.length);
        for(int i = 0; i < array.length; i++){
            a.setElement(i, array[i]);
        }
        return a;
    }
}

class Node {
    double value = 0.0;
    int index = 0;
    Node next;
    public Node(double value, int index) {
        this.index = index;
        this.value = value;
    }

    void setNext(Node next){
        this.next = next;
    }
}