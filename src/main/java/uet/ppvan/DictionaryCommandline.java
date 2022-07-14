package uet.ppvan;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class DictionaryCommandline {
    
    private DictionaryManagement manager;
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
                this.isRunning = false;
                System.out.println("Bye!");
                break;
            case ADD:
                manager.addNewWord();
                break;
            case DELETE:
                manager.deleteWord();
                break;
            case UPDATE:
                manager.updateWord();
                break;
            case INSERT_FROM_COMMANDLINE:
                manager.insertFromCommandline();
                break;
            case INSERT_FROM_FILE:
                manager.insertFromFile();
                break;
            case EXPORT_TO_FILE:
                manager.exportToFile();
                break;
            case SEARCH:
                manager.dictionaryLookup();
                break;
            case SEARCH_INTERACTIVE:
                manager.search();
                break;
            case SHOW_ALL:
                showAllWords();
                break;
        }
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
    
    public void clearScreen() {
        String osName = System.getProperty("os.name");
        if (osName.contains("Windows")) {
            try {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.print("\033\143");
        }
    }
    
    public DictionaryCommandline() {
        manager = new DictionaryManagement();
    }
    
    
    private void showAllWords() {
        System.out.println("Word list.");
        List<Word> words = manager.getAllWords();
        for (int i = 0; i < words.size(); i++) {
            Word word = words.get(i);
            System.out.printf("%d.\t%-10s\t%-10s\n",i , word.getTarget(), word.getExplain());
        }
    }
}
