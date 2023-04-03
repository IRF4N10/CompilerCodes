import java.io.*;
import java.util.Scanner;
import java.util.Vector;
import java.util.regex.*;

public class eliminateLR {
    public static String alpha(String S) {
        String a = S;
        int i = 0;
        while (i < S.length() && S.charAt(i) == ' ') {
            i++;
        }
        a = S.substring(i + 1, S.length());
        return a;
    }
    public static void checker(Vector<String> l) {
        String nonTerminal = "^[A-Z]$";
        String prevNonTer="null";
        Pattern nonTer = Pattern.compile(nonTerminal);
        for (String S : l) {
            int state = 0;
            Vector<String> alphas = new Vector<String>();
            Vector<String> betas = new Vector<String>(); 
            System.out.println("\nThe input grammar given is:\n"+S);
            String splits[] = S.replace("->", "#").split("#");
                for (String w : splits) {
                    Matcher validNonTer = nonTer.matcher(w);
                    String splitRHS[] = w.replace("|", "#").split("#");

                    for (String w2 : splitRHS) {
                        char RHS[] = w2.toCharArray();
                        int j = 0;
                        while (j < RHS.length && RHS[j] == ' ') {
                            j++;
                          
                        }
                        if (RHS[j] == prevNonTer.charAt(0)) {
                            alphas.add(alpha(w2).trim());
                            state =1;
                        }
                        else if(state ==1){
                            betas.add(w2.trim());
                        }                      
                    }
                    if (validNonTer.matches()) {
                        prevNonTer = w;
                    }
                }
                System.out.println("\nThe output will be:");
                if(betas.isEmpty()){
                    System.out.println("Invalid");
                }
                else{
                System.out.printf("%s->",prevNonTer);
                int it =0;
                for(String one:betas){
                   if(it==alphas.size()-1)
                    System.out.printf("%s%s'",one,prevNonTer);
                   else
                    System.out.printf("%s%s'|",one,prevNonTer);
                    it++;                     
                }
                System.out.println();
                System.out.printf("%s'-> ",prevNonTer);
                
                for(String two:alphas){                       
                        System.out.printf("%s%s'|",two,prevNonTer); 
                }
                    System.out.printf("$\n");
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