package uet.ppvan;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

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
            String target = InputHelper.getString("Word target: ");
            String explain = InputHelper.getString("Word explain: ");
            
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
        
        String target = InputHelper.getString("Enter word: ");
        Optional<Word> matches = dictionary.search(target);
        if (matches.isPresent()) {
            Word word = matches.get();
            System.out.println("Found!");
            System.out.printf("%s: %s\n", word.getTarget(), word.getExplain());
        } else {
            System.out.println("Not found any. Try again");
        }
    }
    
    
    
    public List<Word> getAllWords() {
        return dictionary.allWords();
    }
    
    public boolean addNewWord() {
        System.out.println("Add new word.");
        String target = InputHelper.getString("Target: ");
        String explain = InputHelper.getString("Explain: ");
        
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
        String target = InputHelper.getString("Word target: ");
        return dictionary.removeTarget(target);
    }
    
    public List<Word> search(String prefix) {
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
    
    public void updateWord(String target, String explain) {
        dictionary
                .search(target)
                .ifPresent((oldWord) -> dictionary.update(oldWord, explain));
    }
}
