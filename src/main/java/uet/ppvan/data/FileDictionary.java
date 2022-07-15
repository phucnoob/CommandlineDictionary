package uet.ppvan.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class FileDictionary implements Dictionary {
    private final List<Word> vocabulary;
    
    public FileDictionary() {
        vocabulary = new ArrayList<>();
    }
    
    public FileDictionary(List<Word> wordList) {
        vocabulary = Objects.requireNonNullElseGet(wordList, ArrayList::new);
    }
    
    @Override
    public boolean add(Word word) {
        if (word == null) {
            return false;
        }
        
        return vocabulary.add(word);
    }
    
    @Override
    public boolean add(List<Word> wordList) {
        return vocabulary.addAll(
                wordList.stream()
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList()));
    }
    
    @Override
    public List<Word> allWords() {
        return vocabulary;
    }
    
    @Override
    public boolean remove(Word word) {
        return vocabulary.remove(word);
    }
    
    @Override
    public boolean removeTarget(String target) {
        return vocabulary
                .removeIf((Word word) -> word.getTarget().equals(target));
    }
    
    @Override
    public boolean update(Word oldWord, String newExplain) {
        Optional<Word> target = vocabulary.stream()
                .filter(word -> word.equals(oldWord))
                .findFirst();
        
        if (target.isPresent()) {
            target.get().setExplain(newExplain);
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public void forEach(Consumer<Word> consumer) {
        vocabulary.forEach(consumer);
    }
    
    @Override
    public List<Word> prefixSearch(String target) {
        return vocabulary
                .stream()
                .filter((word -> word.getTarget().startsWith(target)))
                .collect(Collectors.toList());
    }
    
    @Override
    public Optional<Word> search(String target) {
        return vocabulary
                .stream()
                .filter((word -> word.getTarget().equals(target)))
                .findFirst();
    }
    
    @Override
    public int length() {
        return vocabulary.size();
    }
}
