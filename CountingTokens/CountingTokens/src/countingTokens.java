import java.util.*;
import java.io.*;
import java.util.Vector;
import java.util.regex.*;
import javax.xml.transform.Source;

public class countingTokens {   
    public static int count = 0, quoFound = 0;
    public static Vector<String> strs = new Vector<String>();

    public static String symbols[] = { "\\+", "\\*", "-", "=", "/", "%", "<", ">", "!", ";", "\\{", "\\}", "\\(",
            "\\)","," };
    public static String operatorAssign[] = { "\\+", "\\*", "-", "=", "/", "%" };
    public static String operatorRelation[] = { "<", ">", "!", "=", "/", "%" };  
    public static String regexExp = "[+-]?[0-9]+(\\.[0-9]+)?([Ee][+-]?[0-9]+)?";
    public static String validIdentifier = "[a-zA-Z]+[a-zA-Z0-9]*";

    public static Pattern symbolPattern = Pattern.compile(String.join("|", symbols));
    public static Pattern assignmentPattern = Pattern.compile(String.join("|", operatorAssign));
    public static Pattern relationPattern = Pattern.compile(String.join("|", operatorRelation));
    public static Pattern identifier = Pattern.compile(validIdentifier);
    public static Pattern expNumberPattern = Pattern.compile(regexExp);

    public static void check(String w) {
        Matcher validIden = identifier.matcher(w);
        Matcher validExpNumber = expNumberPattern.matcher(w);
        try{
            if (validIden.find() && validIden.start() == 0 && quoFound == 0) { //If validIdentifier at the first then it will be Identifier
                count++;
                System.out.printf("Token %d is : %s\n", count, validIden.group());
            }
        if (validExpNumber.find() && validExpNumber.start() == 0 && validExpNumber.end() == w.length()) {           //If validNumber at the first then it will be a number or invalidIdentifier
            count++;           
            System.out.printf("Token %d is : %s\n",count,validExpNumber.group());
        } 
        }
        catch (Exception e) {
            System.out.println();
        }
        for (int i = 0; i < w.length(); i++) {
            String letters = Character.toString(w.charAt(i));
            Matcher symbols = symbolPattern.matcher(letters);
            Matcher relationalOp = relationPattern.matcher(letters);
            if (i < w.length() - 1 && letters.equals("\"") || quoFound == 1) {          //For Checking strings
                quoFound = 1;
                int j = i + 1;               
                while ((j < w.length() && !Character.toString(w.charAt(j)).equals("\""))) {
                    j++;
                }
                strs.add(w.substring(i, j));
                if (j == w.length() && !Character.toString(w.charAt(j-1)).equals("\"") ) {
                    i = j;
                    continue;
                } else if (j < w.length() && Character.toString(w.charAt(j)).equals("\"") && quoFound == 1) {
                    quoFound = 0;
                    count++;
                    System.out.printf("Token %d is : ", count);
                     int stringelement = 0;
                    for (String str : strs) {
                        if (stringelement < strs.size() - 1) {
                            System.out.printf("%s ", str);
                        }
                        else {
                            System.out.printf("%s", str);
                        }
                        stringelement++;
                    }
                    System.out.printf("\"\n");
            
                    i = j; 
                }
                
            }                
            else if (symbols.matches()) {               
                if (i < w.length() - 1 && letters.equals("+")) {                //For getting multipleSymbol operators(++,+=)
                    String nextLetter = Character.toString(w.charAt(i + 1));
                    if (nextLetter.equals("+")||nextLetter.equals("=")) {
                        count++;
                        System.out.printf("Token %d is : %s%s\n",count,w.charAt(i),w.charAt(i + 1));
                        i++;  
                    }                                     
                }
                else if (i < w.length() - 1 && letters.equals("-")) {           //For getting multipleSymbol operators(--,-=)
                    String nextLetter = Character.toString(w.charAt(i + 1));
                   if (nextLetter.equals("-")||nextLetter.equals("=")) {
                        count++;
                        System.out.printf("Token %d is : %s%s\n",count,w.charAt(i),w.charAt(i + 1));
                        i++;  
                    } 
                }
                else if (i < w.length() - 1 && letters.equals("=")) {           //For getting multipleSymbol operators(==,=+,=-,=*...)
                    String nextLetter = Character.toString(w.charAt(i + 1));
                    Matcher assignOp = assignmentPattern.matcher(nextLetter);
                   if (assignOp.matches()) {
                        count++;
                        System.out.printf("Token %d is : %s%s\n",count,w.charAt(i),w.charAt(i + 1));
                        i++;  
                    } 
                }
                else if (i < w.length() - 1 && relationalOp.matches()) {                   //For getting multipleSymbol relationalOperators(<=,>=,!=)
                    if ((Character.toString(w.charAt(i + 1)).equals("="))) {
                        count++;
                        System.out.printf("Token %d is : %s%s\n", count, w.charAt(i), w.charAt(i + 1));
                        i++;
                    }
                }
            
                else {
                    count++;
                    System.out.printf("Token %d is : %s\n",count,letters);
                }               
            }
       }       
    }
    public static void separator(Vector<String> l) {
        int commentFound = 0;
        System.out.println("The tokens for the given input is counted below,");
        for (String lines : l) {
            String splitS[] = lines.trim().split("\\s");
            for (String w : splitS) {
                if (w.length() > 1 && w.charAt(0) == '/' && w.charAt(1) == '/') {       //Checking for Single-line Comment
                    break;
                }
                if (w.length() > 1 && w.charAt(0) == '/' && w.charAt(1) == '*') {       //Checking for multi-line Comment
                    commentFound = 1;
                    break;
                }
                if (w.length() > 1 && commentFound == 1 && w.charAt(0) == '*' && w.charAt(1) == '/') {
                    commentFound = 0;
                    continue;
                }
                if (commentFound != 1) {
                    check(w);
                }
            }
        }
        System.out.printf("\n\tThere are total %d number of tokens in the given input lines.\n",count);
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
        separator(lines);
    }
}