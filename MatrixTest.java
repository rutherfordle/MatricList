
import java.util.Scanner;
import java.io.*;

public class MatrixTest {

    public static void main(String[] args) {

        Matrix A = new Matrix(4);
        Matrix B = new Matrix(4);
        int a = 1;
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 4; j++) {
                A.changeEntry(i, j, a * 1.0);
                a++;
            }
        }
        a = 1;
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 3; j++) {
                B.changeEntry(i, j, a * 1.0);
                a++;
            }
        }

        System.out.print("Matrix A has " + A.getNNZ() + " NNZ values at length " + A.getSize() + "x" + A.getSize() + ".\n" + A.toString());
        System.out.print("Matrix B has " + B.getNNZ() + " NNZ values at length " + B.getSize() + "x" + B.getSize() + ".\n" + B.toString());

        Matrix C = A.add(B);
        System.out.print("Matrix C (ADD) has " + C.getNNZ() + " NNZ values at length " + C.getSize() + "x" + C.getSize() + ".\n" + C.toString());

        Matrix D = A.copy();
        System.out.print("Matrix D (copy) has " + D.getNNZ() + " NNZ values at length " + D.getSize() + "x" + D.getSize() + ".\n" + D.toString());

        Matrix F = A.scalarMult(2.0);
        System.out.print("Matrix F (ScalerMulti: 2xA) has " + F.getNNZ() + " NNZ values at length " + F.getSize() + "x" + F.getSize() + ".\n" + F.toString());

        Matrix G = A.sub(B);
        System.out.print("Matrix G (SUB) has " + G.getNNZ() + " NNZ values at length " + G.getSize() + "x" + G.getSize() + ".\n" + G.toString());

        Matrix H = A.transpose();
        System.out.print("Matrix H (traspose) has " + H.getNNZ() + " NNZ values at length " + H.getSize() + "x" + H.getSize() + ".\n" + H.toString());

        Matrix E = A.mult(B);
        System.out.print("Matrix E (multi) has " + E.getNNZ() + " NNZ values at length " + E.getSize() + "x" + E.getSize() + ".\n" + E.toString());
        a = 1;
        System.out.println("\n \nCreating new Matrix w/o loops: A:" + a);
        A = new Matrix(4);
        B = new Matrix(4);

        B.changeEntry(1, 2, a * 9.0);
        B.changeEntry(1, 3, a * 2.0);
        B.changeEntry(1, 4, a * 3.0);

        B.changeEntry(2, 1, a * 3.0);
        B.changeEntry(2, 4, a * 4.0);

        B.changeEntry(3, 2, a * 1.0);
        B.changeEntry(3, 3, a * 2.0);
        B.changeEntry(3, 4, a * 3.0);

        B.changeEntry(4, 1, a * 4.0);
        B.changeEntry(4, 2, a * 3.0);
        B.changeEntry(4, 3, a * 1.0);
        B.changeEntry(4, 4, a * 7.0);

        A.changeEntry(1, 1, a * 5.0);
        A.changeEntry(1, 3, a * 9.0);
        A.changeEntry(1, 2, a * 6.0);
        A.changeEntry(1, 3, a * 3.0);

        A.changeEntry(2, 3, a * 2.0);
        A.changeEntry(2, 2, a * 9.0);

        A.changeEntry(3, 2, a * 1.0);
        A.changeEntry(3, 3, a * 2.0);
        A.changeEntry(3, 4, a * 9.0);
        A.changeEntry(3, 1, a * 5.0);

        A.changeEntry(4, 1, a * 2.0);
        A.changeEntry(4, 3, a * 9.0);
        A.changeEntry(4, 4, a * 1.0);
        System.out.println("------------------------------------------------");
        C = A.copy();
        System.out.println("A\n" + C.toString());
        System.out.println("C\n" + A.toString());
        System.out.println("------------------------------------------------");
        System.out.print("Matrix A has " + A.getNNZ() + " NNZ values at length " + A.getSize() + "x" + A.getSize() + ".\n" + A.toString());
        System.out.print("Matrix B has " + B.getNNZ() + " NNZ values at length " + B.getSize() + "x" + B.getSize() + ".\n" + B.toString());
        System.out.println("------------------------------------------------");
        C = A.copy();
        System.out.println("A\n" + C.toString());
        System.out.println("C\n" + A.toString());
        System.out.println("------------------------------------------------");
        Matrix I = A.add(B);
        System.out.print("Matrix I (ADD) has " + I.getNNZ() + " NNZ values at length " + I.getSize() + "x" + I.getSize() + ".\n" + I.toString());
        System.out.println("------------------------------------------------");
        C = A.copy();
        System.out.println("A\n" + C.toString());
        System.out.println("C\n" + A.toString());
        System.out.println("------------------------------------------------");
        Matrix J = A.copy();
        System.out.print("Matrix J (copy) has " + J.getNNZ() + " NNZ values at length " + J.getSize() + "x" + J.getSize() + ".\n" + J.toString());
        System.out.println("------------------------------------------------");
        C = A.copy();
        System.out.println("A\n" + C.toString());
        System.out.println("C\n" + A.toString());
        System.out.println("------------------------------------------------");
        Matrix K = A.scalarMult(3.0);
        System.out.print("Matrix K (ScalerMulti: 2xK) has " + K.getNNZ() + " NNZ values at length " + K.getSize() + "x" + K.getSize() + ".\n" + K.toString());
        System.out.println("------------------------------------------------");
        C = A.copy();
        System.out.println("A\n" + C.toString());
        System.out.println("C\n" + A.toString());
        System.out.println("------------------------------------------------");
        Matrix L = A.sub(B);
        System.out.print("Matrix L (SUB) has " + L.getNNZ() + " NNZ values at length " + L.getSize() + "x" + L.getSize() + ".\n" + L.toString());
        System.out.println("------------------------------------------------");
        C = A.copy();
        System.out.println("A\n" + C.toString());
        System.out.println("C\n" + A.toString());
        System.out.println("------------------------------------------------");
        Matrix M = A.transpose();
        System.out.print("Matrix M (traspose) has " + M.getNNZ() + " NNZ values at length " + M.getSize() + "x" + M.getSize() + ".\n" + M.toString());
        System.out.println("------------------------------------------------");
        C = A.copy();
        System.out.println("A\n" + C.toString());
        System.out.println("C\n" + A.toString());
        System.out.println("------------------------------------------------");
        Matrix N = A.mult(B);
        System.out.print("Matrix N (multi) has " + N.getNNZ() + " NNZ values at length " + N.getSize() + "x" + N.getSize() + ".\n" + N.toString());
        System.out.println("------------------------------------------------");
        C = A.copy();
        System.out.println("A\n" + C.toString());
        System.out.println("C\n" + A.toString());
        System.out.println("------------------------------------------------");
        if (A.equals(C)) {
            System.out.println("good news!");
        } else {
            System.out.println("bad news!");
        }
        if (A.equals(M)) {
            System.out.println("bad news!");
        } else {
            System.out.println("good news!");
        }
        if (A.equals(A)) {
            System.out.println("good news!");
        } else {
            System.out.println("bad news!");

        }
        System.out.print("Matrix A has " + A.getNNZ() + " NNZ values at length " + A.getSize() + "x" + A.getSize() + ".\n" + A.toString());
        A.makeZero();
        System.out.println("Make zero matrix A:");
        System.out.println("Matrix A has " + A.getNNZ() + " NNZ values at length " + A.getSize() + "x" + A.getSize() + ".\n" + A.toString());

        A.makeZero();
        B.makeZero();
        A = new Matrix(4);
        B = new Matrix(4);
        a = 1;
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 4; j++) {
                A.changeEntry(i, j, a * 1.0);
                a++;
            }
        }
        a = 1;
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 3; j++) {
                B.changeEntry(i, j, a * 1.0);
                a++;
            }
        }
        System.out.println(A.toString());
        System.out.println(B.toString());

        C = A.copy();
        D = B.copy();
        System.out.println(C.toString());
        System.out.println(D.toString());
    }
}
