package uet.ppvan.commandline;

import java.util.Scanner;

public class InputHelper {
    private final Scanner scanner;
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
            System.err.printf("'%s' is not valid integer!!\n", userInput);
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
    
    public static String getString(String prompt) {
        System.out.print(prompt);
        return getString();
    }
    
    public static String getStringOrDefault(String defaultStr) {
        String str = getString();
        return str.isEmpty() ? defaultStr : str;
    }
    
    public static void idle() {
        helper.scanner.nextLine();
    }
}
