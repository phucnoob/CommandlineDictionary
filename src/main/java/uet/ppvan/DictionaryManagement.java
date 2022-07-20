package uet.ppvan;

import uet.ppvan.data.Dictionary;
import uet.ppvan.data.FileDictionary;
import uet.ppvan.data.Word;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class DictionaryManagement {
    private final Dictionary dictionary;
    
    public DictionaryManagement() {
        dictionary = new FileDictionary();
    }
    
    public int insertFromCommandline(List<Word> words) {
        words.forEach(dictionary::add);
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
    
    public void insertFromFileNio(String path) {
        handleInsertFromFileNonBlock(Path.of(path));
    }
    private int handleInsertFromFile(File src) {
        
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
                dictionary.add(Word.from(parsedToken[0], parsedToken[1]));
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
    
    private int handleInsertFromFileNonBlock(Path path) {
        AtomicInteger count = new AtomicInteger();
        try (Stream<String> fileLines = Files.lines(path)){
            fileLines
                    .map((String line) -> line.split("\t"))
                    .filter(tokens -> tokens.length == 2)
                    .map(tokens -> Word.from(tokens[0], tokens[1]))
                    .forEach(word ->{
                        count.addAndGet(1);
                        dictionary.add(word);
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        return count.get();
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
            return dictionary.add(Word.from(target, explain));
        }
    }
    
    public boolean deleteWord(String target) {
        System.out.println("This will remove all words has target(all definitions).");
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
