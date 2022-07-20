package uet.ppvan.commandline;

import uet.ppvan.DictionaryManagement;
import uet.ppvan.data.Word;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DictionaryCommandline {
    
    private final DictionaryManagement manager;
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
                addNewWord();
                break;
            case DELETE:
                deleteWord();
                break;
            case UPDATE:
                updateWord();
                break;
            case INSERT_FROM_COMMANDLINE:
                insertFromCommandline();
                break;
            case INSERT_FROM_FILE:
                insertFromFile();
                break;
            case EXPORT_TO_FILE:
                exportToFile();
                break;
            case SEARCH:
                dictionaryLookup();
                break;
            case SEARCH_INTERACTIVE:
                interactiveSearch();
                break;
            case SHOW_ALL:
                showAllWords();
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
    
        manager.insertFromCommandline(words);
    }
    
    private void dictionaryLookup() {
        String target = InputHelper.getString("Search word: ");
        Optional<Word> matches = manager.dictionaryLookup(target);
        
        if (matches.isPresent()) {
            Word word = matches.get();
            System.out.println("Found!");
            showWords(List.of(word));
        } else {
            System.out.println("Not found any. Try again");
        }
    }
    
    private void exportToFile() {
        System.out.print("Enter export location(default=dictionaries.txt): ");
        String path = InputHelper.getStringOrDefault("dictionaries.txt");
        boolean success = manager.exportToFile(path);
        
        if (success) {
            System.out.printf("Export data to %s successfully.\n", path);
        } else {
            System.out.println("Export failed.");
        }
    }
    
    private void insertFromFile() {
        System.out.println("Enter path (default = dictionaries.txt): ");
        String path = InputHelper.getStringOrDefault("dictionaries.txt");
        
        manager.insertFromFile(path);
    }
    
    private void addNewWord() {
        System.out.println("Add new word.");
        String target = InputHelper.getString("Target: ");
        String explain = InputHelper.getString("Explain: ");
        
        manager.addNewWord(target, explain);
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
        boolean success = manager.deleteWord(target);
        
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
        
        String wordFormat = "%d.\t%-10s\t%-10s\n";
        System.out.printf("%s.\t%-10s\t%-10s\n", "No", "English", "Vietnamese");
        for (int i = 0; i < wordList.size(); i++) {
            Word word = wordList.get(i);
            System.out.printf(wordFormat, i + 1 , wordList.get(i).getTarget(), wordList.get(i).getExplain());
        }
    }
}
