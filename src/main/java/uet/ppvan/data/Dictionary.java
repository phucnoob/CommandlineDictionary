package uet.ppvan.data;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public interface Dictionary {
    boolean add(Word word);
    
    default boolean add(List<Word> wordList) {
        wordList.forEach(this::add);
        return true;
    }
    
    List<Word> allWords();
    
    boolean remove(Word word);
    
    boolean remove(String target);
    
    boolean update(Word oldWord, String newExplain);
    
    void forEach(Consumer<Word> consumer);
    
    List<Word> prefixSearch(String target);
    
    Optional<Word> get(String target);
    
    int length();
}
