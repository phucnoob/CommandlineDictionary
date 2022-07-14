package uet.ppvan;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class DictionaryManagement {
    private Dictionary dictionary;
    
    public DictionaryManagement() {
        dictionary = new Dictionary();
    }
    
    public int insertFromCommandline() {
        System.out.println("Enter number of words to import: ");
        System.out.print("Words = ");
        int numOfWords = InputHelper.getInt();
        
        for (int i = 0; i < numOfWords; i++) {
            System.out.println("Word target: ");
            String target = InputHelper.getString();
            System.out.println("Word explain: ");
            String explain = InputHelper.getString();
            
            dictionary.add(Word.of(target, explain));
        }
        
        return numOfWords;
    }
    
    public void insertFromFile() {
        System.out.println("Enter path (default = dictionaries.txt): ");
        String path = InputHelper.getStringOrDefault("dictionaries.txt");
        File file = new File(path);
        if (!file.exists()) {
            System.err.println("File not exist. Abort operation...");
            return;
        }
        int records = handleInsertFromFile(file);
        System.out.printf("Import %s: %d success\n", path, records);
    }
    
    public int handleInsertFromFile(File src) {
        
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
    
    public void dictionaryLookup() {
        
        System.out.print("Enter word: ");
        String target = InputHelper.getString();
        List<Word> matches = dictionary.search(target);
    
        System.out.println("Match words: ");
        System.out.printf("%s.\t%-10s\t%-10s\n", "No", "English", "Vietnamese");
        for (int i = 0; i < matches.size(); i++) {
            Word word = matches.get(i);
            System.out.printf("%d.\t%-10s\t%-10s\n",i + 1 , word.getTarget(), word.getExplain());
        }
    }
    
    
    
    public List<Word> getAllWords() {
        return dictionary.allWords();
    }
    
    public boolean addNewWord() {
        System.out.println("Add new word.");
        System.out.print("Target: ");
        String target = InputHelper.getString();
        System.out.print("Explain: ");
        String explain = InputHelper.getString();
        
        if (target.isEmpty() || explain.isEmpty()) {
            System.err.println("Add new word failed.");
            System.err.println("Retry..");
            return false;
        } else {
            return dictionary.add(Word.of(target, explain));
        }
    }
    
    public boolean deleteWord() {
        System.out.println("Delete a word.");
        System.out.println("This will remove all words has target(all definitions).");
        System.out.print("Word target: ");
        
        String target = InputHelper.getString();
        return dictionary.removeTarget(target);
    }
    
    public List<Word> search() {
        String prefix = "";
        return dictionary.prefixSearch(prefix);
    }
    
    public void exportToFile() {
    
        System.out.print("Path(default=dictionaries.txt): ");
        String path = InputHelper.getStringOrDefault("dictionaries.txt");
        File dest = new File(path);
        
        List<Word> words = dictionary.allWords();
        StringBuilder builder = new StringBuilder();
        words.forEach((word ->
                builder.append(word.getTarget())
                        .append('\t')
                        .append(word.getExplain())
                        .append('\n')));
        
        try (FileOutputStream out = new FileOutputStream(dest)) {
            out.write(builder.toString().getBytes(StandardCharsets.UTF_8));
            System.out.printf("Export to %s successfull.\n", path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void updateWord() {
    }
}
