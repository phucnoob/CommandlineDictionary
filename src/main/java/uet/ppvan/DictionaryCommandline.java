package uet.ppvan;

public class DictionaryCommandline {
    
    private DictionaryManagement manager;
    
    public static void main(String[] args) {
        DictionaryCommandline self = new DictionaryCommandline();
        
        self.dictionaryBasic();
    }
    
    public DictionaryCommandline() {
        manager = new DictionaryManagement();
    }
    
    public void dictionaryBasic() {
         manager.insertFromCommandline();
         manager.showAllWords();
    }
    
}
