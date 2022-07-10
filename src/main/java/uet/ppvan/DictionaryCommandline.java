package uet.ppvan;

public class DictionaryCommandline {
    
    private DictionaryManagement manager;
    
    public static void main(String[] args) {
        DictionaryCommandline self = new DictionaryCommandline();
        
//        self.dictionaryBasic();
        self.dictionaryAdvanced();
    }
    
    public DictionaryCommandline() {
        manager = new DictionaryManagement();
    }
    
    public void dictionaryBasic() {
         manager.insertFromCommandline();
         manager.showAllWords();
    }
    
    public void dictionaryAdvanced() {
        manager.insertFromFile();
        manager.dictionaryLookup("book").forEach(System.out::println);
        manager.showAllWords();
    }
}
