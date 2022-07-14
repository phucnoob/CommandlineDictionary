package uet.ppvan;

import java.io.File;
import java.util.Collections;
import java.util.List;

public class DictionaryCommandline {
    
    private DictionaryManagement manager;
    
    public static void main(String[] args) {
        DictionaryCommandline self = new DictionaryCommandline();
        
        self.dictionaryBasic();
//        self.dictionaryAdvanced();
    }
    
    public DictionaryCommandline() {
        manager = new DictionaryManagement();
    }
    
    public void dictionaryBasic() {
         manager.insertFromCommandline();
         
         showAllWords();
    }
    
    private void showAllWords() {
        manager.getAllWords().forEach(System.out::println);
    }
    
    /**
     * Find the word that match target.
     */
    public void dictionarySearcher() {
        System.out.println("Type the target: ");
        String target = InputHelper.getString();
        
        List<Word> matches = manager.search(target);
        // Do something with matches
    }
    
    public void dictionaryAdvanced() {
        manager.insertFromFile();
        manager.dictionaryLookup("book").forEach(System.out::println);
        showAllWords();
    }
    
    public void dictionaryFinal() {
        manager.insertFromFile();
        dictionarySearcher();
        manager.exportToFile(new File("dictionaries.txt"));
    }
}
