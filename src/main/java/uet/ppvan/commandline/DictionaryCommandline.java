package uet.ppvan.commandline;

import uet.ppvan.gui.HelloWorld;
import uet.ppvan.presenter.DictionaryPresenter;
import uet.ppvan.data.Word;
import uet.ppvan.utils.Command;
import uet.ppvan.utils.InputHelper;
import uet.ppvan.view.CommandlineView;
import uet.ppvan.view.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DictionaryCommandline {
    
    private final DictionaryPresenter presenter;
    private final View view;
    private boolean isRunning = true;
    
    public static void main(String[] args) {
        DictionaryCommandline self = new DictionaryCommandline();
        while (self.isRunning) {
            self.showOptions();
            self.takeCommand();
            self.waitUser();
        }
    }
    
    public void waitUser() {
        System.out.println("---Press [enter] to continue----");
        InputHelper.idle();
    }
    
    private void takeCommand() {
        Command command = Command.from(InputHelper.getInt());
    
        switch (command) {
            case EXIT:
                exit();
                break;
            case ADD:
                view.addView();
                break;
            case DELETE:
                view.deleteView();
                break;
            case UPDATE:
                view.updateView();
                break;
            case INSERT_FROM_COMMANDLINE:
                insertFromCommandline();
                break;
            case INSERT_FROM_FILE:
                view.insertFromFileView();
                break;
            case EXPORT_TO_FILE:
                view.exportToFileView();
                break;
            case SEARCH:
                view.lookupView();
                break;
            case SEARCH_INTERACTIVE:
                view.searchView();
                break;
            case SHOW_ALL:
                view.getView();
                break;
        }
    }
    
    private void exit() {
        this.isRunning = false;
        System.out.println("Bye!");
        System.exit(0);
    }
    
    private void insertFromCommandline() {
        System.out.println("Enter number of words to import: ");
        System.out.print("Words = ");
        
        List<Word> words = new ArrayList<>();
        int numOfWords = InputHelper.getInt();
    
        for (int i = 0; i < numOfWords; i++) {
            System.out.printf("Word %d:\n", i + 1);
            String target = InputHelper.getString("Word target: ");
            String explain = InputHelper.getString("Word explain: ");
            
            if (target.isEmpty() || explain.isEmpty()) {
                i--;
                System.err.println("Empty entry is invalid! Enter again.");
                continue;
            }
        
            words.add(Word.from(target, explain));
        }
    
        presenter.insertFromCommandline(words);
    }
    
    
    public void showOptions() {
        System.out.println("Commandline Dictionary Management Program.");
        System.out.println("1. Add new word.");
        System.out.println("2. Delete a word.");
        System.out.println("3. Update a word.");
        System.out.println("4. Insert from commandline(Add many words).");
        System.out.println("5. Insert from file.");
        System.out.println("6. Export to file.");
        System.out.println("7. Search word. ");
        System.out.println("8. Search word (interactive).");
        System.out.println("9. Show all words.");
        System.out.println("0. Exit.");
        System.out.print("Command[0-9]: ");
    }
    
    public DictionaryCommandline() {
        presenter = new DictionaryPresenter();
        view = new CommandlineView();
    }
}
