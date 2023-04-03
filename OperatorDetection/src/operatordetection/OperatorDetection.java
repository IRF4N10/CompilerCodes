package operatordetection;
import java.io.*;
import java.util.Scanner;
import java.util.Vector;
import java.util.regex.*;

public class OperatorDetection {
    static void detector(Vector<String> l) {
        char unaryO[] = { '+', '-', '*', '/', '%'};
        char relationO[] = { '<', '>', '!' };
        for (String S : l) {
        int state = 0, i = 0;
            char line[] = S.toCharArray();
            while (i < S.length()) {
                if (i < S.length()) {
                    if (line[i] == '=') {
                        state = 3;
                    }
                    if (line[i] == '^') {
                        System.out.printf("\nBitwise operator found: (%c)", line[i]);
                        i++;
                        continue;
                    }
                    if (line[i] == '~') {
                        System.out.printf("\nBitwise operator found: (%c)", line[i]);
                        i++;
                        continue;
                    }
                    if (line[i] == ',') {
                        System.out.printf("\nComma operator found: (%c)", line[i]);
                        i++;
                        continue;
                    }
                }
                for (int j = 0; j < unaryO.length; j++) {
                    if (line[i] == unaryO[j]) {
                        state = 1;
                        break;
                    }
                }
                for (int j = 0; j < relationO.length; j++) {
                    if (line[i] == relationO[j]) {
                        state = 2;
                        break;
                    }
                }
                i++;       
                if (S.length() == i && state == 1) {                    
                        System.out.printf("\nArithmetic operator found: (%c)", line[i-1]);
                        i++;                        
                }
                else if (i < S.length() && state == 1) {
                    if (line[i] == '=') {
                        System.out.printf("\nAssignment operator found: (%c=)", line[i - 1]);
                        i++;
                    } else if (line[i] == '+'&& line[i-1] == '+') {
                        System.out.printf("\nIncrement Operator found: (++)");
                        i++;
                    } else if (line[i] == '-'&& line[i-1] == '-') {
                        System.out.println("\nDecrement Operator found: (--)");
                        i++;
                    }
                    else {
                        System.out.printf("\nArithmetic operator found: (%c)", line[i-1]);
                    }
                    state = 0;
                }
                if (S.length() == i && state == 2) {
                       System.out.printf("\nRelational Operator found: (%c)", line[i - 1]);
                       i++;
                 }
                 else if (i < S.length() && state == 2) {
                     if (line[i] == '=') {
                         System.out.printf("\nRelational Operator found: (%c%c)", line[i - 1], line[i]);
                         i++;
                     } else if (line[i - 1] == '<' && line[i] == '<') {
                         System.out.printf("\nBitwise Shift left operator found: (%c%c)", line[i - 1], line[i]);
                         i++;
                     } else if (line[i - 1] == '>' && line[i] == '>') {
                         System.out.printf("\nBitwise Shift right operator found: (%c%c)", line[i - 1], line[i]);
                         i++;
                     } else if (line[i - 1] == '!') {
                         System.out.printf("\nLogical operator found: (%c)", line[i - 1]);
                     } else {
                         System.out.printf("\nRelational Operator found: (%c)", line[i - 1]);
                     }
                     state = 0;
                 }
                 if(S.length()==i && state ==3){
                    System.out.printf("\nAssignment operator found: (%c)", line[i - 1]);
                    i++;
                 }
                 else if (i < S.length() && state == 3) {
                    if (line[i] == '=') {
                        System.out.printf("\nRelational Operator found: (%c%c)", line[i-1],line[i]);
                        i++;
                    }                   
                    else {
                        System.out.printf("\nAssignment operator found : (%c)", line[i - 1]);
                    }                        
                    state = 0;
                }                
            }
        }   
    }
    public static void main(String[] args) throws Exception {
Scanner I = new Scanner(System.in);
        File myFile = new File("Text.txt");

        if (myFile.createNewFile()) {
        System.out.println("File created: " + myFile.getName());
      }
        Vector<String> lines = new Vector<String>();
        try {
            Scanner F = new Scanner(myFile);
            while (F.hasNextLine()) {
                String line = F.nextLine();
                lines.add(line);
            }
            F.close();
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        }
        detector(lines);
    }
}