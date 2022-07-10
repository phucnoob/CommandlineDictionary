package uet.ppvan;

import java.io.*;
import java.util.Collections;
import java.util.List;
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
    
    public int insertFromFile() {
        return insertFromFile(new File("dictionaries.txt"));
    }
    
    public int insertFromFile(File src) {
        
        int addedWords = 0;
        try(FileReader input = new FileReader(src);
            LineNumberReader buffer = new LineNumberReader(input)) {
            String line;
            while ((line = buffer.readLine()) != null) {
                String[] parsedToken = line.split("\t");
                if (parsedToken.length != 2) {
                    System.err.println("Invalid line at: " + buffer.getLineNumber());
                    continue;
                }
                dictionary.add(Word.of(parsedToken[0], parsedToken[1]));
                addedWords++;
            }
            
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
        } catch (IOException ioException) {
            System.err.println("Unknown IO exception...\n" + ioException.getMessage());
            ioException.printStackTrace();
        }
        return addedWords;
    }
    
    public int showAllWords() {
        System.out.println("Word list: ");
        dictionary.forEach(System.out::println);
        return dictionary.length();
    }
    
}
