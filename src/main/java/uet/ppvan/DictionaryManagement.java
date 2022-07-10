package uet.ppvan;

import java.util.Scanner;

public class DictionaryManagement {
    private Dictionary dictionary;
    
    public DictionaryManagement() {
        dictionary = new Dictionary();
    }
    
    public int insertFromCommandline() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of words to import: ");
        System.out.print("Words = ");
        int numOfWords = scanner.nextInt();
        // Remove the '\n' cause by nextInt
        scanner.nextLine();
        
        for (int i = 0; i < numOfWords; i++) {
            System.out.println("Word target: ");
            String target = scanner.nextLine();
            System.out.println("Word explain: ");
            String explain = scanner.nextLine();
            
            dictionary.add(Word.of(target, explain));
        }
        
        scanner.close();
        return numOfWords;
    }
    
    public int showAllWords() {
    
        System.out.println("Word list: ");
        dictionary.forEach(System.out::println);
        return dictionary.length();
    }
    
}
