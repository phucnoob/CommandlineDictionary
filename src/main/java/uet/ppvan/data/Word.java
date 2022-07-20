package uet.ppvan.data;

public class Word {
    private String target;
    private String explain;
    
    private Word(String target, String explain) {
        this.target = target;
        this.explain = explain;
    }
    
    public static Word from(String target, String explain) {
        if (isInValid(target)) {
            System.err.println("Target should not be null, empty or blank");
            return null;
        }
        if (isInValid(explain)) {
            System.err.println("Explain should not be null, empty or blank");
            return null;
        }
        return new Word(target, explain).validate();
    }
    private Word validate() {
        this.target = this.target.trim();
        this.explain = this.explain.trim();
        return this;
    }
    
    private static boolean isInValid(String str) {
        if (str == null) {
            return true;
        } else if (str.isEmpty()) {
            return true;
        } else if (str.isBlank()) {
            return true;
        }
        
        return false;
    }
    public String getTarget() {
        return target;
    }
    
    public void setTarget(String target) {
        
        if (isInValid(target)) {
            return;
        }
        this.target = target.trim();
    }
    
    public String getExplain() {
        return explain;
    }
    
    public void setExplain(String explain) {
        
        if (isInValid(explain)) {
            return;
        }
        
        this.explain = explain.trim();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Word) {
            Word other = (Word) obj;
            return this.target.equals(other.getTarget())
                    && this.explain.equals(other.getExplain());
        }
        
        return false;
    }
    
    @Override
    public String toString() {
        return  this.target + ": "
                + this.explain;
    }
    
}
