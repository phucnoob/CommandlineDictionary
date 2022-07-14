package uet.ppvan;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Dictionary {
    private List<Word> vocabulary;
    
    public Dictionary() {
        vocabulary = new ArrayList<>();
    }
    
    public Dictionary(List<Word> wordList) {
        vocabulary = Objects.requireNonNullElseGet(wordList, ArrayList::new);
    }
    
    public boolean add(Word word) {
        if (word == null) {
            return false;
        }
        
        return vocabulary.add(word);
    }
    
    public boolean add(List<Word> wordList) {
        return vocabulary.addAll(
                wordList.stream()
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList()));
    }
    
    public List<Word> allWords() {
        return vocabulary;
    }
    
    public boolean remove(Word word) {
        return vocabulary.remove(word);
    }
    
    public boolean removeTarget(String target) {
        return vocabulary
                .removeIf((Word word) -> word.getTarget().equals(target));
    }
    
    public boolean update() {
        return false;
    }
    
    public void forEach(Consumer<Word> consumer) {
        vocabulary.forEach(consumer);
    }
    
    public List<Word> prefixSearch(String target) {
        return vocabulary
                .stream()
                .filter((word -> word.getTarget().startsWith(target)))
                .collect(Collectors.toList());
    }
    
    public List<Word> search(String target) {
        return vocabulary
                .stream()
                .filter((word -> word.getTarget().equals(target)))
                .collect(Collectors.toList());
    }
    
    public int length() {
        return vocabulary.size();
    }
}
