package uet.ppvan;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import uet.ppvan.utils.InputHelper;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class InputHelperTest {
    
    static InputStream sysBackup;
    @BeforeAll
    static void init() {
        sysBackup = System.in;
    }
    
    @AfterEach
    void resetSystemIn() throws IOException {
        System.in.close();
        System.setIn(sysBackup);
    }
    
    @Test
    void shouldIgnoreNotNumberInput() {
        String input = "abcbs";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        int value = InputHelper.getInt();
        assertEquals(0, value);
    }
    @Test
    void shouldReturnEmptyStringOnEmptyInput() {
        String input = "";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        String str = InputHelper.getString();
        assertEquals("", str);
    }
    
    @Test
    void shouldReturnEmptyStringOnNewlineInput() {
        String input = "\n\n\n\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        String str = InputHelper.getString();
        assertEquals("", str);
    }
}