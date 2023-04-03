package keywordidentifier;
import java.io.*;
import java.util.Scanner;
import java.util.Vector;
import java.util.regex.*;

public class KeywordIdentifier {
    static void detector(Vector<String> l) {
        String validIdentifier = "^([a-zA-Z_$][a-zA-Z\\d_$]*)$";
        String seperator = "(\\s|,|;|-|--|%|\\+|\\+\\+|\\*|\\/|=|==)";
        String keywords[] ={"int","float","double","char","short","long","boolean","void","continue","break","do","while","if","else","switch","goto","jump","for","else if","auto","const","unsigned","register","struct","union","global","return","extern","case","typedef","enum"};
        Pattern keywordPattern = Pattern.compile(String.join("|",keywords));
        Pattern identifier = Pattern.compile(validIdentifier);
      
        for (String S : l) {
            String splits[] = S.split(seperator);
            for (String w : splits) {
                Matcher validI = identifier.matcher(w);
                Matcher validKeyword = keywordPattern.matcher(w);
                if (validI.matches()) {
                    if(validKeyword.matches()){
                      System.out.println("Keyword found = " +w);
                }
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
        detector(lines);
    }
}