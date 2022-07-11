package uet.ppvan;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordTest {
    
    @Test
    void EmptyTargetShouldReturnNull() {
        Word word = Word.of("", "testing");
        assertNull(word);
    }
    
    @Test
    void EmptyExplainShouldReturnNull() {
        Word word = Word.of("testing", "");
        assertNull(word);
    }
    
    @Test
    void BlankExplainShouldReturnNull() {
        Word word = Word.of("testing", "          ");
        assertNull(word);
    }
    
    @Test
    void BlankTargetShouldReturnNull() {
        Word word = Word.of("testing", "            ");
        assertNull(word);
    }
    
    @Test
    void setTargetShouldDoNothingOnBlankTarget() {
        Word word = Word.of("testing", "testing");
        
        assert word != null;
        word.setTarget("            ");
        String newTarget = word.getTarget();
        
        assertEquals("testing", newTarget);
    }
    
    @Test
    void setTargetShouldTrimTheString() {
        Word word = Word.of("testing trim", "test");
        assert word != null;
        word.setTarget("     some target that not very clean        ");
        
        assertEquals("some target that not very clean", word.getTarget());
    }
    
    @Test
    void setExplainShouldTrimTheString() {
        Word word = Word.of("testing trim", "test");
        assert word != null;
        word.setExplain("     some explain that not very clean        ");
        
        assertEquals("some explain that not very clean", word.getExplain());
    }
    
    @Test
    void setExplainShouldDoNothingOnBlankExplain() {
        Word word = Word.of("testing", "testing");
        
        assert word != null;
        word.setExplain("            ");
        String newExplain = word.getExplain();
        
        assertEquals("testing", newExplain);
    }
    
    @Test
    void testEqualsContract() {
        Word word = Word.of("testing", "testing");
        Word other = Word.of("testing", "testing");
        Word otherWord = Word.of("testing", "testing");
    
        assert word != null;
        assert other != null;
        assert otherWord != null;
        // A word should equal itself
        assertEquals(word, word);
        // if wordA = wordB than wordB = wordA
        assertEquals(word.equals(other), other.equals(word));
        // if wordA = wordB and wordB = wordC then wordA = wordC
        assertEquals(word.equals(otherWord), word.equals(other) && other.equals(otherWord));
    }
}