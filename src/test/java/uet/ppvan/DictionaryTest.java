package uet.ppvan;

import org.junit.jupiter.api.Test;
import uet.ppvan.data.FileDictionary;
import uet.ppvan.data.Word;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DictionaryTest {
    
    @Test
    void ShouldNotAddNullWord() {
        Word word = null;
        FileDictionary dictionary = new FileDictionary();
        dictionary.add(word);
        
        assertEquals(0, dictionary.length());
    }
    
    @Test
    void ShouldIgnoreNullWordInCollection() {
        ArrayList<Word> words = new ArrayList<>();
        words.add(Word.of("dummy", "data1"));
        words.add(Word.of("dummy", "data2"));
        words.add(Word.of("dummy", "data3"));
        words.add(null);
        words.add(null);
        words.add(Word.of("dummy", "data4"));
        words.add(Word.of("dummy", "data5"));
        
        FileDictionary dictionary = new FileDictionary();
        dictionary.add(words);
        
        assertEquals(5, dictionary.length());
    }
    
    @Test
    void shouldRemoveOneWord() {
        FileDictionary dictionary = new FileDictionary();
        dictionary.add(Word.of("hello", "bye"));
        dictionary.add(Word.of("hello", "bye1"));
        
        dictionary.remove(Word.of("hello", "bye1"));
    }
}