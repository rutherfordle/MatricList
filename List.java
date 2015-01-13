/* Rutherford Le
 * Assignment 3
 * Rle
 */

class List {

    private class Node {
        // Fields

        Object data;
        Node next;
        Node prev;

        // Constructor
        Node(Object data) {
            this.data = data;
            prev = next = null;
        }
        // toString:  Overides Object's toString method.

        @Override
        public String toString() {
            return String.valueOf(data);
        }
    }
    // Fields
    private Node front;
    private Node back;
    private Node current;
    private int length;

    // Constructor
    List() {
        //Creates a new empty List.
        front = back = current = null;
        length = 0;
    }

    // Access Functions 
    int getLength() {
        // getLength(): returns length of this List      
        return length;
    }

    boolean isEmpty() {
        // isEmpty(): returns true if this is an empty list, false otherwise
        //makes front node null, causing the other nodes to be collected by garbage collect
        return front == null;
    }

    boolean offEnd() {
        // offend(): Returns true if current is undefined.
        return current == null;
    }

    public boolean equals(Object x) {
        // Returns true if this List and L are the same Objecteger
        // sequence. Ignores the current element in both Lists.
        boolean flag = false;
        Node check = front;
        List L;
        //int i = 0;
        if (x instanceof List) {
            L = (List) x;
            flag = (getLength() == L.getLength());
            moveTo(0);
            L.moveTo(0);
            for (int i =1; i<=getLength();i++){
                flag = (getLength() == L.getLength());
                if (!flag)
                    return flag;
            }
        }
        return flag;
    }

    int getIndex() {
        // If current element is defined, returns its position in this List,            		
        // ranging from 0 to getLength()-1 inclusive.
        // If current element is undefined, returns -1.

        if (current == null) {
            return -1;
        } else {
            int position = 0;
            Node M = front;
            while (M != current) {
                M = M.next;
                position++;
            }

            return position;
        }
    }

    Object getFront() {
        // Returns front element. Pre: !isEmpty().
        if (isEmpty()) {
            throw new RuntimeException("List Error: getFront() called on empty List");
        }
        return front.data;
    }

    Object getBack() {
        // Returns back element. Pre: !isEmpty().
        if (isEmpty()) {
            throw new RuntimeException("List Error: getBack() called on empty List");
        }
        return back.data;
    }

    Object getCurrent() {
        // Returns current element. Pre: !isEmpty(), !offEnd().

        if (isEmpty()) {
            throw new RuntimeException("List Error: getCurrent() called on empty List");
        }
        if (offEnd()) {
            throw new RuntimeException("List Error: getCurrent() called on undefined current");
        }
        return current.data;
    }
// Manipulation Procedures ///

    void makeEmpty() {
        // Sets this List to the empty state. Post: isEmpty().
        while (!isEmpty()) {
            deleteFront();
        }
    }

    void moveTo(int i) {
        // If 0 <= i <= getLength()-1, moves current element
        // marker to position i in this List. Otherwise current
        // element becomes undefined.
        // Moves current one step toward front element. 
        // Pre: !isEmpty(), !offEnd().

        current = front;

        if (0 <= i && i <= (length - 1)) {

            for (int j = 0; j < i; j++) {
                moveNext();
            }
        } else {
            current = null;
        }
    }

    void movePrev() {
        // Moves current one step toward front element. 
        // Pre: !isEmpty(), !offEnd(). 

        if (isEmpty()) {
            throw new RuntimeException("List Error: movePrev() called on empty list");
        }
        current = current.prev;
    }

    void moveNext() {
        // Moves current one step toward back element.
        // Pre: !isEmpty(), !offEnd().
        if (isEmpty()) {
            throw new RuntimeException("List Error: moveNext() called on empty list");
        }
        if (offEnd()) {
            throw new RuntimeException("List Error: moveNext() called on offEnd list");
        }

        current = current.next;
    }

    void insertFront(Object data) {
        // Inserts new element before front element.
        // Post: !isEmpty().
        Node newNode = new Node(data);
        if (isEmpty()) {
            front = back = newNode;
        } else {
            newNode.prev = null;
            front.prev = newNode;
            newNode.next = front;
            front = newNode;
        }
        length++;
    }

    void insertBack(Object data) {
        // Inserts new element after back element.
        // Post: !isEmpty().
        Node newNode = new Node(data);
        //if (back == null) {
        if (isEmpty()) {
            back = front = newNode;
        } else {
            newNode.next = null;
            back.next = newNode;
            newNode.prev = back;
            back = newNode;
        }
        length++;
    }

    void insertBeforeCurrent(Object data) {
        // Inserts new element before current element.
        // Pre: !isEmpty(), !offEnd().
        if (isEmpty()) {
            throw new RuntimeException("List Error: insertBeforeCurrent() is empty.");
        }
        if (offEnd()) {
            throw new RuntimeException("List Error: insertBeforeCurrent() is undefined.");
        }

        Node node = new Node(data);
        if (current == front) {
            node.prev = null;
            current.prev = node;
            node.next = current;
            front = node;
        } else {
            node.prev = current.prev;
            current.prev.next = node;
            node.next = current;
            current.prev = node;
        }

        length++;
    }

    void insertAfterCurrent(Object data) {
        // Inserts new element after current element.
        // Pre: !isEmpty(), !offEnd().
        if (isEmpty()) {
            throw new RuntimeException("List Error: insertBeforeCurrent() is empty.");
        } else if (offEnd()) {
            throw new RuntimeException("List Error: insertBeforeCurrent() is undefined.");
        }
        Node node = new Node(data);
        node.prev = current;
        node.next = current.next;
        if (current == back) {
            current.next = node;
            node.next = null;
            node.prev = current;
            back = node;
        } else {
            current.next.prev = node;
            node.next = current.next;
            current.next = node;
            node.prev = current;
        }

        length++;

    }

    void deleteFront() {
        // Deletes front element. Pre: !isEmpty().
        if (isEmpty()) {
            throw new RuntimeException("List Error: deleteFront() is empty");
        }
        if (current == front) {
            current = null;
        }
        if (front == back) {
            front = back = null;
        }
        if (front != back) {
            front = front.next;
            front.prev = null;
        }

        //no condition for front == current
        length--;
    }

    void deleteBack() {
        // Deletes back element. Pre: !isEmpty().
        if (isEmpty()) {
            throw new RuntimeException("List Error: deleteBack() is empty");
        }
        if (current == back) {
            current = null;
        }
        if (back == front) {
            front = back = null;
        } else {
            back = back.prev;
            back.next = null;

        }
        length--;
    }

    void deleteCurrent() {
        // Deletes current element.
        // Pre: !isEmpty(), !offEnd(); Post: offEnd()
        // Other methods
        if (isEmpty()) {
            throw new RuntimeException("List Error: deleteCurrent() is empty");
        }
        if (offEnd()) {
            throw new RuntimeException("List Error: deleteCurrent() is undefined");
        }
        if (!isEmpty() && !offEnd()) {
            if (current == front) {
                deleteFront();
            } else if (current == back) {
                deleteBack();
            } else {
                current.prev.next = current.next;
                current.next.prev = current.prev;
                current = null;
            }

        }
        length--;
    }

    @Override
    public String toString() {
        // Overrides Object's toString method. Returns a string
        // representation of this List consisting of a space
        // separated sequence of Objectegers, with no trailing space.
        String display = "";
        //adds each element to display as the loop traverses
        for (Node N = front; N != null; N = N.next) {
            //to remove the last whitespace at the end of the list,
            //adds parenthesses
            if (N.next == null) {
                display += N.toString();
                //return display;

            } else {
                display += N.toString() + " ";
            }
        }
        String syntax = "(" + display + ")";
        return syntax;
    }
}