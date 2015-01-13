/* Rutherford Le
 * CMPS 101
 * Rle
 */

import java.util.Scanner;
import java.io.*;

public class Sparse {

    public static void main(String[] args) throws IOException {

        //setup io variables
        Scanner in = null;
        PrintWriter out = null;
        String line = null;
        String[] token = null;
        int i, n = 0;
        int lineNumber = 0;

        //If no input file, output message and exit
        if (args.length < 2) {
            out.println("Usage: FileIO infile outfile");
            System.exit(1);
        }
        //initialize in/out scanners
        in = new Scanner(new File(args[0]));
        out = new PrintWriter(new FileWriter(args[1]));

        //tokenize line 1 for NNZ values and matrix size
        lineNumber++;

        line = in.nextLine() + " ";
        token = line.split("\\s+");
        
        int mSize = Integer.parseInt(token[0]); //stores matrix size
        int Annz = Integer.parseInt(token[1]); //stores A NNZ
        int Bnnz = Integer.parseInt(token[2]); //stores B NNZ
        
        //creates new Matrixes based on the size provided
        Matrix A = new Matrix(mSize);
        Matrix B = new Matrix(mSize);

        //variables used in the parameter of changeEntry
        int row;
        int col;
        double val;

        //truth values for showing whether a matrix is complete or not.
        boolean isA = false;
        boolean isB = false;

        //while loop that goes through each line of the input file
        while (in.hasNextLine()) {
            //starting on line 2 and tokenizes new line.
            lineNumber++;
            line = in.nextLine() + " ";
            token = line.split("\\s+");
            n = token.length;

            //loop that reads in and collects values for matrix A.
            if (n != 0 && !isA) {

                for (int a = 1; a <= Annz; a++) {

                    //collects row,col,val data and inserts into matrix.
                    row = Integer.parseInt(token[0]);
                    col = Integer.parseInt(token[1]);
                    val = Double.parseDouble(token[2]);
                    A.changeEntry(row, col, val);

                    //increments line and tokenizes if there's a line.
                    lineNumber++;
                    if (in.hasNextLine()) {
                        line = in.nextLine() + " ";
                        token = line.split("\\s+");
                        n = token.length;
                    }
                }
                isA = true;
            }


            //loop that reads in and collects values for matrix B.
            if (n != 0 && !isB) {
                for (int b = 1; b <= Bnnz; b++) {

                    //collects row,col,val data and inserts into matrix.
                    row = Integer.parseInt(token[0]);
                    col = Integer.parseInt(token[1]);
                    val = Double.parseDouble(token[2]);
                    B.changeEntry(row, col, val);

                    //increments line and tokenizes if there's a line.
                    lineNumber++;
                    if (in.hasNextLine()) {
                        line = in.nextLine() + " ";
                        token = line.split("\\s+");
                        n = token.length;
                    }
                }
                isB = true;
            }
        }//end of while loop

        //System.out.println(A.toString());
        //System.out.println(B.toString());

        //Matrix A and B are completely initalized. Calling appropriately methods
        out.print("A has " + A.getNNZ() + " non-zero entries:\n");
        out.print(A.toString());

        out.print("B has " + B.getNNZ() + " non-zero entries:\n");
        out.print(B.toString());

        out.print("(1.5)*A =\n");
        Matrix scal = A.scalarMult(1.5);
        out.print(scal.toString());

        out.print("A+B =\n");
        Matrix add = A.add(B);
        out.print(add.toString());

        out.print("A+A =\n");
        Matrix C = A.copy();
        Matrix add2 = A.add(C);
        out.print(add2.toString());

        out.print("B-A =\n");
        Matrix sub = B.sub(A);
        out.print(sub.toString());

        out.print("A-A =\n");
        Matrix sub2 = C.sub(A);
        out.print(sub2.toString());

        out.print("Transpose(A) =\n");
        Matrix trans = A.transpose();
        out.print(trans.toString());

        out.print("A*B =\n");
        Matrix multi = A.mult(B);
        out.print(multi.toString());

        out.print("B*B =\n");
        C = B.copy();
        Matrix multi2 = C.mult(B);
        out.print(multi2.toString());

        in.close();
        out.close();
    }
}
