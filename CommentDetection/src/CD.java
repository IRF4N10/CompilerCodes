import java.io.*;
import java.util.*;

public class CD {

    static void isComment(Vector<String> l) {
        int state = 0;
        for (String S : l) {
    
            char line[] = S.toCharArray();
            int i = 0, j = 0;
            if (state == 2) {
                while (i < line.length && line[i] != '*') {
                    i++;
                    j = 0;
                }
                 if (i == line.length) {
                        continue;
                    }
                while (j < line.length && line[j] != '*' && line[j + 1] != '/') {
                    j++;
                }    
            }
            else {
                while (i < line.length && line[i] != '/') {
                    i++;
                    j = i + 2;
                }
                if (i == line.length)
                        continue;
                while (j + 1 < line.length && line[j] != '*' && line[j + 1] != '/') {
                    j++;
                }
            }
              
                          
            if (i!=line.length && line[i] == '/' && line[i + 1] == '/') {
                state = 1;
            } else if (i != line.length && line[i] == '/' && line[i + 1] == '*') {
                state = 2;
            } 
            else if (state == 2 && j<line.length  && line[j] == '*' && line[j+ 1] == '/') {
                state = 3;

            }else if (i != line.length) {
                state = 0;
            }       
            if (state == 2 && j < line.length && line[j] == '*' && line[j + 1] == '/') {
                state = 3;
            }            
        }
         if (state == 1)
                System.out.printf("\n\nA single line comment is found");

            else if (state == 3)
                System.out.printf("\n\nA multi line comment is found");

            else if(state == 0)
                System.out.println("There is no comment");
    }        
    public static void main(String[] args) {
        Scanner I = new Scanner(System.in);
        File myFile = new File("Text.txt");
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
        isComment(lines);
    }
}