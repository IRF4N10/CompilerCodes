import java.util.*;
import java.io.*;
import java.util.Vector;
import java.util.regex.*;

public class numberTypeDetection {
    static int checker(String w) {
        int result = 0;
        String validExp = "[+-]?[0-9]+(\\.[0-9]+)?([Ee][+-]?[0-9]+)";
        String validRealN = "[+-]?[0-9]+(\\.[0-9]+)";
        String validInteger = "[+-]?[0-9]+";
        Pattern exponentialNumber = Pattern.compile(validExp);
        Pattern realNumber = Pattern.compile(validRealN);
        Pattern integer = Pattern.compile(validInteger);
        Matcher validE = exponentialNumber.matcher(w);
        Matcher validR = realNumber.matcher(w);
        Matcher validI = integer.matcher(w);
        if (validE.matches()) {
            result = 1;
        } else if (validR.matches()) {
            result = 2;
        } else if (validI.matches()) {
            result = 3;
        }    
        return result;
    }
    public static void result(String w) {
        int result = checker(w);
        if (result == 1) {
            System.out.println("Exponential number = " + w);
        } else if (result == 2) {
            System.out.println("The real number = " + w);
        } else if (result == 3) {
            System.out.println("Integer number = " + w);
        }
    }
        public static int state = 0, state1 = 0, state2 = 0;
        static void subSeparator(String w) {
            int i = 0, k = 0;
            char digits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
            Pattern p = Pattern.compile("\\+|-");

            while (k < w.length()) {
                for (int j = 0; j < digits.length; j++) {
                    if (i < w.length() && w.charAt(i) == digits[j]) {
                        i++;
                        if (w.charAt(i) == '.') {
                            i++;
                        }
                        continue;
                    }
                }
                if (i < w.length() && w.charAt(i) == 'E' || w.charAt(i) == 'e') {
                    i++;
                    if (i < w.length() && w.charAt(i) == '+' || w.charAt(i) == '-') {
                        state = 1;
                        i++;
                        for (int j = 0; j < digits.length; j++) {
                            if (i < w.length() && w.charAt(i) == digits[j]) {
                                i++;
                                continue;
                            }
                        }
                    }
                }
                k++;
            }
            result(w.substring(0, i));
            String sub = w.substring(i, w.length());
            String splitW[] = sub.split("\\+|-", 2);
            Matcher m = p.matcher(splitW[1]);
            if (m.find()) {
                try{
                    subSeparator(splitW[1]);
                }
                catch (Exception e) {
                    System.out.println("Invalid number given");
                }
            }
            else {
                result(splitW[1]);
            }          
        }       
        static void separator(Vector<String> l) {
        String separate = "(\\s|,|;|--|%|\\+\\+|\\*|\\/|=|==|\\(|\\))";
        Pattern p = Pattern.compile("\\+|-");

        for (String S : l) {
            String splitS[] = S.split(separate);
            for (String w : splitS) {
                Matcher m = p.matcher(w);
                if(m.find()&&checker(w)!=1)
                    subSeparator(w);
                else 
                    result(w);                                          
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
        separator(lines);
        }
}