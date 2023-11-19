package com.oleg.command;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ReadFromFileTest extends CommandTester {
    @BeforeAll
    public static void setUpCommand() {
        command = new ReadFromFile("reader");
    }

    @Test
    public void readWithNoArgsTest() {
        var expected = Optional.empty();

        assertEquals(expected, command.execute(company));
    }

    @Test
    public void readDefaultTest() {
        var expected = company.getTariffList();

        assertEquals(expected, command.execute(company, "default").get());
    }

    @Test
    public void readFileNameTest() {
        var expected = company.getTariffList();

        assertEquals(expected, command.execute(company, "filename default.txt").get());
    }
}