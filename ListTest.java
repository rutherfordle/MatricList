/* Rutherford Le
 * Rle
 */

class ListTest {

    public static void main(String[] args) {
        // Allocate several List Objects, and manipulate 
        // them in various ways.  Call each of the above 
        // ADT operations at least once. 
        List A = new List();
        List B = new List();
        System.out.println("-----------------------------");
        System.out.println("Testing getIndex()" + A);
        System.out.println(A.getIndex());
        System.out.println("-----------------------------");
        System.out.println("\nTesting insertfront()");
        for (int i = 1; i <= 10; i++) {
            A.insertFront(i * 2 - 4);
            B.insertFront(i * 3 - 2);
        }
        System.out.println("List A: " + A.toString() + ", List B: " + B.toString());
        System.out.println("" + ListStatus(A));
        System.out.println("" + ListStatus(B));

        System.out.println("-----------------------------");
        System.out.println("Testing insertBack(17)");
        A.insertBack(17);
        System.out.println("List A: " + A.toString());
        System.out.println("" + ListStatus(A));

        System.out.println("-----------------------------");
        System.out.println("Testing deleteBack()");
        A.deleteBack();
        System.out.println("List A: " + A.toString());
        System.out.println("" + ListStatus(A));

        System.out.println("-----------------------------");
        System.out.println("testing deleteFront");
        A.deleteFront();
        System.out.println("List A: " + A.toString());
        System.out.println("" + ListStatus(A));
        System.out.println("-----------------------------");

        if (A.equals(B)) {
            System.out.println("A is equal to B");
        } else {
            System.out.println("A is not equal");
        }
        System.out.println("A List: " + A.toString());
        System.out.println("B List: " + B.toString());
        System.out.println("-----------------------------");

        System.out.println("Test: moveTo(2), getIndex(), toString()");
        A.moveTo(2);
        System.out.println("A" + PrintCurr(A));

        System.out.println("------------------------");
        System.out.println("Deletes current");
        A.deleteCurrent();
        System.out.println("" + A.toString());
        System.out.println("" + ListStatus(A));
        A.moveTo(2);
        System.out.println("A" + PrintCurr(A));
        System.out.println("-----------------------------");
        System.out.println("Insert after current");
        A.insertAfterCurrent(69);
        System.out.println("" + A.toString());
        System.out.println("A" + PrintCurr(A));

        System.out.println("-----------------------------");
        System.out.println("Insert before current");
        A.insertBeforeCurrent(96);
        System.out.println("" + A.toString());
        System.out.println("A" + PrintCurr(A));
        System.out.println("-----------------------------");

        System.out.println("Testing make Empty");
        System.out.println("A" + A.getLength());
        A.makeEmpty();
        B.makeEmpty();
        System.out.println("A" + A.getLength());
        System.out.println("A: " + A.toString());
        System.out.println("A index" + A.getIndex());
        System.out.println("B: " + B.toString());
        System.out.println("B index" + B.getIndex());

        System.out.println("----------------------------------");
        System.out.println("Testing copy and equals");
        for (int i = 1; i <= 10; i++) {
            A.insertFront(i * 2 - 4);
            B.insertFront(i * 2 - 4);
        }
        System.out.println("List A: " + A.toString());
        System.out.println("List B: " + A.toString());
        System.out.println("-----------------------------------");
        System.out.println("we want true" + A.equals(B));

        System.out.println("List A: " + A.toString());
        System.out.println("List B: " + A.toString());
        
        System.out.println("-----------------------------------");
        A.deleteBack();
        System.out.println("List A: " + A.toString());

        System.out.println("Test: moveTo(4), getIndex(), toString()");
        A.moveTo(4);
        System.out.println("" + A.getIndex() + " " + A.getCurrent() + "  "
                + A.toString());
        System.out.println("-----------------------------");

        System.out.println("-----------------------------");


    }

    private static String ListStatus(List O) {
        String dis = dis = (": Front: = " + O.getFront() + ", Back: = " + O.getBack() + ", Length = " + O.getLength());
        return dis;
    }

    private static String PrintCurr(List O) {
        String Curr = (": index = " + O.getIndex() + ", curr data = " + O.getCurrent());
        return Curr;
    }
}