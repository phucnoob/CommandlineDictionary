package uet.ppvan.presenter;

import uet.ppvan.data.Dictionary;
import uet.ppvan.data.FileDictionary;
import uet.ppvan.data.Word;
import uet.ppvan.view.View;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class DictionaryPresenter implements Presenter {
    private final Dictionary dictionary;
    private View currentView;
    
    public DictionaryPresenter() {
        dictionary = new FileDictionary();
    }
    
    @Override
    public void setView(View view) {
        currentView = view;
    }
    public int insertFromCommandline(List<Word> words) {
        words.forEach(dictionary::add);
        return words.size();
    }
    public void insertFromFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            currentView.error("File not exist. Abort operation...");
            return;
        }
        int records = handleInsertFromFile(file);
        currentView.info(String.format("Import %s: %d success\n", path, records));
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
                    currentView.error("Invalid line at: " + buffer.getLineNumber());
                    continue;
                }
                dictionary.add(Word.from(parsedToken[0], parsedToken[1]));
                addedWords++;
            }
            
        } catch (FileNotFoundException ex) {
            currentView.error(String.format("No such file: %s", src.getAbsolutePath()));
        } catch (IOException ioException) {
            currentView.error("Unknown IO exception...\n" + ioException.getMessage());
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
    
    @Override
    public List<Word> getAllWords() {
        return dictionary.allWords();
    }
    
    @Override
    public void addNewWord(String target, String explain) {
        if (target.isEmpty() || explain.isEmpty()) {
            currentView.error("Empty word is not allowed");
        } else {
            dictionary.add(Word.from(target, explain));
        }
    }
    
    @Override
    public void deleteWord(String target) {
        boolean deleted = dictionary.remove(target);
        if (deleted) {
            currentView.info(String.format("Delete %s successfully.", target));
        } else {
            currentView.error("Delete failed.");
        }
    }
    
    public List<Word> search(String prefix) {
        return dictionary.prefixSearch(prefix);
    }
    
    @Override
    public void exportToFile(String path) {
        File dest = createFileIfNotExist(path);
        
        List<Word> words = dictionary.allWords();
        StringBuilder builder = new StringBuilder();
        words.forEach((word ->
                builder.append(word.getTarget())
                        .append('\t')
                        .append(word.getExplain())
                        .append('\n')));
        
        handleWriteToFile(dest, builder.toString()
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
    
    @Override
    public void updateWord(String target, String explain) {
        Optional<Word> founded = dictionary.get(target);
        
        if (founded.isPresent()) {
            dictionary.update(founded.get(), explain);
        } else {
        
        }
    }
    
    @Override
    public void searchWord(String target) {
        if (target == null || target.isEmpty() || target.isBlank()) {
            currentView.error("Can't not search for empty/blank target.");
        }
        
        List<Word> result = dictionary.prefixSearch(target);
        currentView.searchResultView(result);
    }
    
    @Override
    public void lookupWord(String target) {
        if (target == null || target.isEmpty() || target.isBlank()) {
            currentView.error("Can't not search for empty/blank target.");
        }
        
        Optional<Word> founded = dictionary.get(target);
        
        if (founded.isPresent()) {
            currentView.lookupResultView(founded.get());
        } else {
            currentView.lookupResultView(null);
        }
    }
}
