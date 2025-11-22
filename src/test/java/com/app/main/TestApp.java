package com.app.main;

// Import JUnit 5
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TestApp {

    @Test
    public void sampleTest() {
        System.out.println("This is a sample test.");
        assertEquals(1,1, "1 should be equal to 1");

    }
}

