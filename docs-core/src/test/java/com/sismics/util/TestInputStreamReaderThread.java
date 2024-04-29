package com.sismics.util;

import com.sismics.util.io.InputStreamReaderThread;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class TestInputStreamReaderThread {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testInputStreamReaderThread() throws IOException {
        // Given
        String inputString = "Test line 1\nTest line 2\nTest line 3";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(inputString.getBytes());
        InputStreamReaderThread readerThread = new InputStreamReaderThread(inputStream, "TestThread");

        // When
        readerThread.start();
        try {
            // Sleep for a short time to allow the reader thread to start and process some lines
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        readerThread.interrupt(); // Interrupt the thread to stop processing lines

        // Then
        String[] outputLines = outContent.toString().split(System.lineSeparator());
        assertEquals(3, outputLines.length); // Ensure that three lines are logged
        assertEquals("TestThread: Test line 1", outputLines[0].trim()); // Check the first line
        assertEquals("TestThread: Test line 2", outputLines[1].trim()); // Check the second line
        assertEquals("TestThread: Test line 3", outputLines[2].trim()); // Check the third line
    }
}
