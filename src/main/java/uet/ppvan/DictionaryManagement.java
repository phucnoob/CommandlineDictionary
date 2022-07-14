package uet.ppvan;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

public class DictionaryManagement {
    private Dictionary dictionary;
    
    public DictionaryManagement() {
        dictionary = new Dictionary();
    }
    
    public int insertFromCommandline(List<Word> words) {
        words.forEach(word -> dictionary.add(word));
        return words.size();
    }
    
    public void insertFromFile(String path) {
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
    
    public Optional<Word> dictionaryLookup(String target) {
        return dictionary.search(target);
    }
    
    
    
    public List<Word> getAllWords() {
        return dictionary.allWords();
    }
    
    public boolean addNewWord(String target, String explain) {
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
    
    public boolean exportToFile(String path) {
        File dest = createFileIfNotExist(path);
        
        List<Word> words = dictionary.allWords();
        StringBuilder builder = new StringBuilder();
        words.forEach((word ->
                builder.append(word.getTarget())
                        .append('\t')
                        .append(word.getExplain())
                        .append('\n')));
        
        return handleWriteToFile(dest, builder.toString()
                .getBytes(StandardCharsets.UTF_8));
    }
    
    public File createFileIfNotExist(String path) {
        File file = new File(path);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
    
    public boolean handleWriteToFile(File dest, byte[] data) {
        try (FileOutputStream out = new FileOutputStream(dest)) {
            out.write(data);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public void updateWord(String target, String explain) {
        dictionary
                .search(target)
                .ifPresent((oldWord) -> dictionary.update(oldWord, explain));
    }
}
