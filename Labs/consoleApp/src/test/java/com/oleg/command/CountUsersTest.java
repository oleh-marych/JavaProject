package com.oleg.command;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class CountUsersTest extends CommandTester {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeAll
    public static void setUpCommand() {
        command = new CountUsers("count");
    }

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void reset() {
        System.setOut(originalOut);
    }

    @Test
    public void countWithNoArgsTest() {
        var expected = Optional.empty();

        assertEquals(expected, command.execute(company));
    }

    @Test
    public void countAllTest() {
        var expected = Optional.empty();

        assertEquals(expected, command.execute(company, "all"));
        Pattern integerPattern = Pattern.compile("-?\\d+");
        Matcher matcher = integerPattern.matcher(outContent.toString());

        String res = "0";
        if (matcher.find()) {
            res = matcher.group();
        }


        assertEquals(9000, Integer.parseInt(res));
    }
}