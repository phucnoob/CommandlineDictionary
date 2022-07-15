package uet.ppvan.data;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public interface Dictionary {
    boolean add(Word word);
    
    boolean add(List<Word> wordList);
    
    List<Word> allWords();
    
    boolean remove(Word word);
    
    boolean removeTarget(String target);
    
    boolean update(Word oldWord, String newExplain);
    
    void forEach(Consumer<Word> consumer);
    
    List<Word> prefixSearch(String target);
    
    Optional<Word> search(String target);
    
    int length();
}
