import java.io.*;
import java.util.Scanner;
import java.util.Vector;

public class EliminateLF {
    public static void checker(Vector<String> l) {
        int k = 0, state = 0;
        String a;
        String headOfProduction = "null";
        String alpha = "null";
        for (String S : l) {
            Vector<String> derived = new Vector<String>();
            Vector<String> betas = new Vector<String>();
            Vector<String> gama = new Vector<String>();
            System.out.println("\nThe input grammar given is:\n" + S);
            String splits[] = S.replace("->", "#").split("#", 2);
            headOfProduction = splits[0].trim();
            String splitRHS[] = splits[1].replace("|", "#").split("#");
            for (String w : splitRHS) {
                derived.add(w.strip());
                if (k < w.trim().length())
                    k = w.trim().length();
            }
            for (int i = 0; i < k; i++) {
                a = String.valueOf(derived.elementAt(0).charAt(i));
                for (int j = 1; j < derived.size(); j++) {
                    String w = derived.elementAt(j);
                    if (i < w.length()) {
                        if (a.equals(String.valueOf(w.charAt(i)))) {
                            state = i + 1;
                        } else {
                            break;
                        }
                    }
                }
            }
             alpha = derived.elementAt(0).substring(0, state);
            for (String w : derived) {
                if (w.startsWith(alpha)) {
                    betas.add(w.substring(state, w.length()));
                }
                else {
                    gama.add(w);
                }
            }
            alpha = derived.elementAt(0).substring(0, state);
             System.out.println("\nOutput wil be,");
             System.out.printf("%s->", headOfProduction);
             System.out.printf("%s%s'", alpha, headOfProduction);
             if (gama.size() > 0) {
                 for (String w : gama) {
                    
                     System.out.printf("| %s", w);
                }
             }            
            System.out.printf("\n%s'->", headOfProduction);
            for (String w : betas) {
                if (w.equals(betas.lastElement())) {
                    System.out.printf("%s", w);
                }
                else {
                    System.out.printf("%s|",w);
                }
            }           
        }     
    }
    public static void main(String[] args) throws Exception {
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
        checker(lines);
    }
}