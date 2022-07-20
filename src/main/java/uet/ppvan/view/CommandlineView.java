package uet.ppvan.view;

import uet.ppvan.data.Word;
import uet.ppvan.presenter.DictionaryPresenter;
import uet.ppvan.presenter.Presenter;
import uet.ppvan.utils.InputHelper;

import java.util.List;

public class CommandlineView implements View {
    
    Presenter presenter;
    @Override
    public void info(String message) {
        System.out.println(message);
    }
    
    @Override
    public void error(String error) {
        System.err.println(error);
    }
    
    public CommandlineView() {
        presenter = new DictionaryPresenter();
        presenter.setView(this);
    }
    
    public void addView() {
        info("Add new word");
        String target = InputHelper.getString("Target: ");
        String explain = InputHelper.getString("Explain: ");
        presenter.addNewWord(target, explain);
    }
    
    public void deleteView() {
        info("Delete existed word.");
        String target = InputHelper.getString("Target: ");
        
        presenter.deleteWord(target);
    }
    
    public void updateView() {
        info("Update existed word.");
        String target = InputHelper.getString("Target: ");
        String newExplain = InputHelper.getString("New Explain: ");
        
        presenter.updateWord(target, newExplain);
    }
    
    public void getView() {
        info("List all words");
        String wordFormat = "%d.\t%-10s\t%-10s\n";
        System.out.printf("%s.\t%-10s\t%-10s\n", "No", "English", "Vietnamese");
        List<Word> wordList = presenter.getAllWords();
        for (int i = 0; i < wordList.size(); i++) {
            Word word = wordList.get(i);
            System.out.printf(wordFormat, i + 1 , wordList.get(i).getTarget(), wordList.get(i).getExplain());
        }
    }
    
    @Override
    public void searchView() {
        info("Type to search");
        String target = InputHelper.getString("Prefix: ");
        
        presenter.searchWord(target);
    }
    
    @Override
    public void searchResultView(List<Word> result) {
        info("Founded");
        String wordFormat = "%d.\t%-10s\t%-10s\n";
        System.out.printf("%s.\t%-10s\t%-10s\n", "No", "English", "Vietnamese");
        for (int i = 0; i < result.size(); i++) {
            Word word = result.get(i);
            System.out.printf(wordFormat, i + 1 , word.getTarget(), word.getExplain());
        }
    }
    
    @Override
    public void lookupView() {
        info("Type to lookup");
        String target = InputHelper.getString("Word target: ");
        presenter.lookupWord(target);
    }
    
    public void lookupResultView(Word founded) {
        if (founded == null) {
            System.out.println("Not found");
            return;
        }
        
        System.out.println("Founded: ");
        System.out.println(founded);
    }
    
    @Override
    public void insertFromFileView() {
        System.out.println("Enter path (default = dictionaries.txt): ");
        String path = InputHelper.getStringOrDefault("dictionaries.txt");
    
        presenter.insertFromFile(path);
    }
    
    @Override
    public void exportToFileView() {
        System.out.print("Enter export location(default=dictionaries.txt): ");
        String path = InputHelper.getStringOrDefault("dictionaries.txt");
        
        presenter.exportToFile(path);
    }
}
