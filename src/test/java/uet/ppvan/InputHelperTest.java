package uet.ppvan;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class InputHelperTest {
    @Test
    void shouldIgnoreNotNumberInput() {
        String input = "abcxyz...\n15";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        int value = InputHelper.getInt();
        assertEquals(15, value);
    }
}