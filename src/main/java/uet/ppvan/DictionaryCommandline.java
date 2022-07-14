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
                System.exit(0);
                break;
            case ADD:
                manager.addNewWord();
                break;
            case DELETE:
                deleteWord();
                break;
            case UPDATE:
                updateWord();
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
                interactiveSearch();
                break;
            case SHOW_ALL:
                showAllWords();
                break;
        }
    }
    
    private void interactiveSearch() {
        String prefix = InputHelper.getString("Enter prefix: ");
        showWords(manager.search(prefix));
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
    
    public void deleteWord() {
        showAllWords();
        String target = InputHelper.getString("Enter word to delete: ");
        boolean success = manager.deleteWord();
        
        if (success) {
            System.out.printf("Delete: %s successfully\n", target);
        } else {
            System.err.printf("Delete: %s failed\n", target);
        }
    }
    
    public void updateWord() {
        showAllWords();
    
        String target = InputHelper.getString("Enter word to update: ");
        String newExplain = InputHelper.getString("New explain: ");
        manager.updateWord(target, newExplain);
        System.out.println("Update word successfully.");
    }
    
    public DictionaryCommandline() {
        manager = new DictionaryManagement();
    }
    
    
    private void showAllWords() {
        showWords(manager.getAllWords());
    }
    
    private void showWords(List<Word> wordList) {
        
        if (wordList == null) {
            return;
        }
        
        System.out.printf("%s.\t%-10s\t%-10s\n", "No", "English", "Vietnamese");
        for (int i = 0; i < wordList.size(); i++) {
            Word word = wordList.get(i);
            System.out.printf("%d.\t%-10s\t%-10s\n",i + 1 , word.getTarget(), word.getExplain());
        }
    }
}
