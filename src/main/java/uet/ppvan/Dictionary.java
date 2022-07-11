package uet.ppvan;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;
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
    
    public boolean remove(Word word) {
        return vocabulary.remove(word);
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        vocabulary.forEach((word) -> builder.append(word.toString()));
        
        return builder.toString();
    }
    
    public void forEach(Consumer<Word> consumer) {
        vocabulary.forEach(consumer);
    }
    
    public int length() {
        return vocabulary.size();
    }
}
