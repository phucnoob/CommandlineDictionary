package uet.ppvan.presenter;

import uet.ppvan.data.Word;
import uet.ppvan.view.View;

import java.util.List;

public interface Presenter {
    void setView(View view);
    
    List<Word> getAllWords();
    
    void addNewWord(String target, String explain);
    
    void deleteWord(String target);
    
    void exportToFile(String path);
    
    void updateWord(String target, String explain);
    
    void searchWord(String target);
    
    void lookupWord(String target);
    
    void insertFromFile(String path);
    
}
