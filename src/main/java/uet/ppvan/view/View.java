package uet.ppvan.view;

import uet.ppvan.data.Word;

import java.util.List;

public interface View {
    void info(String message);
    void error(String error);
    void addView();
    
    void updateView();
    void deleteView();
    void getView();
    
    void searchView();
    void searchResultView(List<Word> result);
    
    void lookupView();
    void lookupResultView(Word founded);
    
    void insertFromFileView();
    default void insertFromFileResultView() {
    
    }
    void exportToFileView();
}
