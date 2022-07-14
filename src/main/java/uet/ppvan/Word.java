package uet.ppvan;

public class Word {
    private String target;
    private String explain;
    
    private Word(String target, String explain) {
        this.target = target;
        this.explain = explain;
    }
    
    public static Word of(String target, String explain) {
        if (!isValidString(target)) {
            System.err.println("Target should not be null, empty or blank");
            return null;
        }
        if (!isValidString(explain)) {
            System.err.println("Explain should not be null, empty or blank");
            return null;
        }
        
        return new Word(target, explain).validate();
    }
    
    /**
     * This method is for testing only.
     */
    public static Word fromTarget(String target) {
        return new Word(target, "testing");
    }
    
    private Word validate() {
        this.target = this.target.trim();
        this.explain = this.explain.trim();
        return this;
    }
    
    static private boolean isValidString(String str) {
        return str != null && !str.isEmpty() && !str.isBlank();
    }
    public String getTarget() {
        return target;
    }
    
    public void setTarget(String target) {
        
        if (!isValidString(target)) {
            return;
        }
        this.target = target.trim();
    }
    
    public String getExplain() {
        return explain;
    }
    
    public void setExplain(String explain) {
        
        if (!isValidString(explain)) {
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
    
    public boolean hasSameTarget(Word other) {
        if (other == null) {
            return false;
        }
        return this.target.equals(other.getTarget());
    }
}
