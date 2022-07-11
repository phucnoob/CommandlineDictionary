package uet.ppvan;

import java.io.BufferedReader;
import java.util.Scanner;

public class InputHelper {
    private Scanner scanner;
    private static InputHelper helper;
    
    static {
        helper = new InputHelper();
    }
    
    private InputHelper() {
        scanner = new Scanner(System.in);
    }
    
    
    public static int getInt() {
        String userInput = getString();
        int value = 0;
        try {
            value = Integer.parseInt(userInput);
        } catch (NumberFormatException ex) {
            System.err.println("Please enter valid number!!");
            value = getInt();
        }
        
        return value;
    }
    
    public static String getString() {
        if (helper.scanner.hasNextLine()) {
            return helper.scanner.nextLine();
        } else {
            return "";
        }
    }
}
