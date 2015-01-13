/*
 * Rutherford Le
 * Rle
 * CS 101
 */
class Matrix {

    //encapsulates the column and value information corresponding to a matrix entry.
    private class Entry {
        //field

        double value;
        int col;

        //constructor
        Entry(int col, double value) {
            this.col = col;
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf("(" + col + ", " + value + ")");

        }

        @Override
        public boolean equals(Object x) {
            boolean eq = false;
            Entry that;
            if (x instanceof Entry) {
                that = (Entry) x;
                if (col == x) {
                    if (value == x) {
                        eq = true;
                    }
                }
            }
            return eq;
        }
    }
    private int size;
    private int NNZ;
    private List[] row;

    Matrix(int dim) {
        size = dim;
        NNZ = 0;
        row = new List[dim + 1];
        for (int i = 1; i <= dim; i++) {
            row[i] = new List();
        }
    }

//General rules for below: n denotes number of rows for A. "a" and "b" will denote the number of non-zero entries in A and B respectively. 
//
// Access functions /////////////////// 
    // Returns n, the number of rows and columns of this Matrix 
    int getSize() {
        return size;
    }

    // Returns the number of non-zero entries in this Matrix 
    int getNNZ() {
        return NNZ;
    }

    // overrides Object's equals() method 
    public boolean equals(Object x) {
        boolean flag = false;
        Matrix M;
        int i = 1;
        if (x instanceof Matrix) {
            M = (Matrix) x;
            flag = (M.getSize() == this.getSize());
            while (flag && i <= size) {
                if ((M.row[i].equals(row[i]))) {
                    flag = true;
                } else {
                    return false;
                }
                i++;
            }
        }

        return flag;
    }

// Manipulation procedures //////////// 
    // sets this Matrix to the zero state    
    void makeZero() {
        Entry n;
        for (int i = 1; i <= size; i++) {
            row[i].makeEmpty();
        }

        NNZ = 0;
    }
    // returns a new Matrix having the same entries as this Matrix    

    Matrix copy() {
        Matrix x = new Matrix(size);
        Entry n;
        for (int i = 1; i <= size; i++) {
            row[i].moveTo(0);
            while (!row[i].offEnd()) {
                x.changeEntry(i, (((Entry) row[i].getCurrent()).col), (((Entry) row[i].getCurrent()).value));
                row[i].moveNext();
            }
        }
        return x;
    }

    void changeEntry(int i, int j, double x) {
        //used in dot, multi, input file, and transpose.
        if (i < 1 || i > getSize()) {
            throw new RuntimeException("Matrix Error: changeEntry out of bounds with:" + i + ", " + j + ", " + x);
        } //if x is zero. Converts NNZ to zero or keeps zero value at zero
        else if (x == 0) {
            if (row[i].getLength() > 0) {
                row[i].moveTo(0);
                while (!row[i].offEnd() && (((Entry) row[i].getCurrent()).col < j)) {   //There should be only one j. Traverses until we're at j to delete the node at (i,j).
                    row[i].moveNext();
                }
                if (!row[i].offEnd()) {
                    if (((Entry) row[i].getCurrent()).col == j) {
                        row[i].deleteCurrent();
                        NNZ--;
                    }
                }
            }
        } //if x is a non-zero. Either converts zero to NNZ, or converts NNZ value to another/same NNZ value.
        else if (x != 0) {
            Entry tmp = new Entry(j, x);
            //if the list is empty
            if (row[i].isEmpty()) {
                row[i].insertBack(tmp);
                NNZ++;
            } else {
                //if the list isn't empty, move to 0 index and traverse through the list.
                row[i].moveTo(0);
                while (!row[i].offEnd() && ((Entry) row[i].getCurrent()).col < j) {
                    row[i].moveNext();
                }
                //If current fell off the list, insert back
                if (row[i].offEnd()) {
                    row[i].insertBack(tmp);
                    NNZ++;
                } else {
                    //if pointing at j, delete j and insertBefore
                    if (((Entry) row[i].getCurrent()).col == j) {
                        row[i].insertAfterCurrent(tmp);
                        System.out.println(row[i].getCurrent());
                        row[i].deleteCurrent();
                        //NNZ doesn't change
                    } else if (((Entry) row[i].getCurrent()).col > j) {
                        //current col is greater than j, no j col, insertBefore and increment NNZ
                        row[i].insertBeforeCurrent(tmp);
                        NNZ++;
                    }
                }
            }
        }
    }

// returns a new Matrix that is the scalar product of this Matrix with x 
    Matrix scalarMult(double x) {
        double tmp;
        Matrix m = new Matrix(size);
        //traverse through the arracy of rows
        for (int i = 1; i <= size; i++) {
            if (!row[i].isEmpty()) {    //not neccessary since !offEnd will return false if the list is empty.
                row[i].moveTo(0);
                while (!row[i].offEnd()) {
                    //multiplies each Entry value in the row with the scalar x and inserts the new value into changeEntry
                    tmp = x * (((Entry) row[i].getCurrent()).value);
                    m.changeEntry(i, (((Entry) row[i].getCurrent()).col), tmp);
                    row[i].moveNext();
                }
            }
        }
        return m;
    }

    // returns a new Matrix that is the sum of this Matrix with M 
    // pre: getSize()==M.getSize()
    Matrix add(Matrix M) {
        //variable declaration
        Matrix C = new Matrix(size);
        List Ltmp;
        double vtmp;
        //loop traverses through the array of rows
        for (int i = 1; i <= size; i++) {
            //uses Ltmp to temperoryly store the list from Add heloper method 
            //which returns a new list containing the addition of two lists
            Ltmp = ADDhelp(row[i], M.row[i]);
            Ltmp.moveTo(0);
            for (int t = 1; t <= Ltmp.getLength(); t++) {
                //traverses through the new list extracting Entry data to insert into row[i] of the matrix C.
                vtmp = ((Entry) Ltmp.getCurrent()).value;
                C.changeEntry(i, ((Entry) Ltmp.getCurrent()).col, vtmp);
                Ltmp.moveNext();
            }
        }
        return C;
    }

    private List ADDhelp(List A, List B) {
        //moves to begining of list.
        A.moveTo(0);
        B.moveTo(0);
        List C = new List();
        Entry etmp;
        double add;
        //Handles offEnd and !offEnd seperatly
        while (!A.offEnd() && !B.offEnd()) {
            //if A col > B col, then A col is zero, so B+0 and increment B curr. Insert this new value into the return list.
            if ((((Entry) A.getCurrent()).col) > (((Entry) B.getCurrent()).col)) {
                add = ((Entry) B.getCurrent()).value;
                etmp = new Entry(((Entry) B.getCurrent()).col, add);
                C.insertBack(etmp);
                B.moveNext();
                //if A col < B col, then B col is zero, so A+0 and increment A curr. Insert this new value into the return list.    
            } else if (((Entry) A.getCurrent()).col < ((Entry) B.getCurrent()).col) {
                add = ((Entry) A.getCurrent()).value;
                etmp = new Entry(((Entry) A.getCurrent()).col, add);
                C.insertBack(etmp);
                A.moveNext();
                //if A col = B col, A+B and increment B curr and A curr. Insert this new value into the return list.    
            } else if (((Entry) A.getCurrent()).col == ((Entry) B.getCurrent()).col) {
                add = ((Entry) A.getCurrent()).value + ((Entry) B.getCurrent()).value;
                etmp = new Entry(((Entry) B.getCurrent()).col, add);
                C.insertBack(etmp);
                B.moveNext();
                A.moveNext();
            }
        }
        //Handles when either A or B goes offend. XOR only.
        while ((A.offEnd() || B.offEnd()) && !(A.offEnd() && B.offEnd())) {
            //A is offend, so insert B values to return list.
            if (A.offEnd() && !B.offEnd()) {
                add = ((Entry) B.getCurrent()).value;
                if (add != 0) {
                    etmp = new Entry(((Entry) B.getCurrent()).col, add);
                    C.insertBack(etmp);
                }
                B.moveNext();
            } //B is offend, so insert A values to return list.
            else if (!A.offEnd() && B.offEnd()) {
                add = ((Entry) A.getCurrent()).value;
                if (add != 0) {
                    etmp = new Entry(((Entry) A.getCurrent()).col, add);
                    C.insertBack(etmp);
                }
                A.moveNext();
            }
        }
        return C;
    }

// returns a new Matrix that is the difference of this Matrix with M 
// pre: getSize()==M.getSize()
    Matrix sub(Matrix M) {
        //essentially the same concept as add
        Matrix C = new Matrix(size);
        List Ltmp;
        double vtmp;
        //Loops through the array of rows.
        for (int i = 1; i <= size; i++) {
            Ltmp = SUBhelp(row[i], M.row[i]);
            Ltmp.moveTo(0);
            for (int t = 1; t <= Ltmp.getLength(); t++) {
                vtmp = ((Entry) Ltmp.getCurrent()).value;
                C.changeEntry(i, ((Entry) Ltmp.getCurrent()).col, vtmp);
                Ltmp.moveNext();
            }
        }
        return C;
    }

    private List SUBhelp(List A, List B) {
        //essentially the same concept as ADDhelp
        //A-B where A = this and B=perameter
        A.moveTo(0);
        B.moveTo(0);
        List C = new List();
        Entry etmp;
        double sub;
        int min = Math.min(A.getLength(), B.getLength());
        while (!A.offEnd() && !B.offEnd()) {
            if (((Entry) A.getCurrent()).col > ((Entry) B.getCurrent()).col) {
                sub = 0 - (((Entry) B.getCurrent()).value);
                etmp = new Entry(((Entry) B.getCurrent()).col, sub);
                C.insertBack(etmp);
                B.moveNext();
            } else if (((Entry) A.getCurrent()).col < ((Entry) B.getCurrent()).col) {
                sub = ((Entry) A.getCurrent()).value;
                etmp = new Entry(((Entry) B.getCurrent()).col, sub);
                C.insertBack(etmp);
                A.moveNext();
            } else if (((Entry) A.getCurrent()).col == ((Entry) B.getCurrent()).col) {
                sub = ((Entry) A.getCurrent()).value - ((Entry) B.getCurrent()).value;
                etmp = new Entry(((Entry) B.getCurrent()).col, sub);
                C.insertBack(etmp);
                B.moveNext();
                A.moveNext();
            }
        }
        while ((A.offEnd() || B.offEnd()) && !(A.offEnd() && B.offEnd())) { //XOR
            if (A.offEnd() && !B.offEnd()) {
                sub = 0 - ((Entry) B.getCurrent()).value;
                etmp = new Entry(((Entry) B.getCurrent()).col, sub);
                C.insertBack(etmp);
                B.moveNext();
            } else if (!A.offEnd() && B.offEnd()) {
                sub = ((Entry) A.getCurrent()).value;
                etmp = new Entry(((Entry) A.getCurrent()).col, sub);
                C.insertBack(etmp);
                A.moveNext();
            }
        }
        return C;
    }

    // returns a new Matrix that is the transpose of this Matrix 
    Matrix transpose() {
        Matrix M = new Matrix(size);
        double tmp;
        int tcol;
        //traverses through the array of rows
        for (int i = 1; i <= size; i++) {
            row[i].moveTo(0);
            while (!row[i].offEnd()) {
                tmp = ((Entry) row[i].getCurrent()).value;
                tcol = ((Entry) row[i].getCurrent()).col;
                M.changeEntry(tcol, i, tmp);
                row[i].moveNext();
            }
        }
        return M;
    }

    // returns a new Matrix that is the product of this Matrix with M 
    // pre: getSize()==M.getSize()
    Matrix mult(Matrix M) {
        Matrix mult = new Matrix(size);
        Matrix trans = M.transpose();
        double tmp;
        for (int i = 1; i <= size; i++) { //traver through the rows
            for (int j = 1; j <= size; j++) { //traver through the rows
                tmp = dot(row[i], trans.row[j]);
                //System.out.println("(" + i + ", " + j + ", " + tmp + ")");
                mult.changeEntry(i, j, tmp);
                //System.out.println("get here?");
            }
        }

        return mult;
    }

    // Helper functions ////
    private static double dot(List A, List B) {
        A.moveTo(0);
        B.moveTo(0);
        double mult = 0;
        while (!A.offEnd() && !B.offEnd()) {
            if (((Entry) A.getCurrent()).col == ((Entry) B.getCurrent()).col) {
                mult += ((Entry) A.getCurrent()).value * ((Entry) B.getCurrent()).value;
                A.moveNext();
                B.moveNext();
            } else if (((Entry) A.getCurrent()).col < ((Entry) B.getCurrent()).col) {
                A.moveNext();
            } else if (((Entry) A.getCurrent()).col > ((Entry) B.getCurrent()).col) {
                B.moveNext();
            }
        }
        return mult;
    }

    // Other functions //////////////////// 
    // overrides Object's toString() method 
    public String toString() {
        // Overrides Object's toString method. Returns a string
        // representation of this List consisting of a space
        // separated sequence of Objectegers, with no trailing space.

        String display = "";
        //adds each element to display as the loop traverses
        //iterates through the rows
        for (int i = 1; i <= size; i++) {

            if (!row[i].isEmpty()) {
                display += (i + ": ");
                row[i].moveTo(0);
                //iterates through the row
                for (int p = 0; p < row[i].getLength(); p++) {
                    if (p == row[i].getLength() - 1) {
                        display += (row[i].getCurrent().toString());

                    } else {
                        display += (row[i].getCurrent().toString() + " ");
                    }
                    row[i].moveNext();
                }
                display += "\n";
            }
        }
        display += "\r\n";
        return display;
    }
}