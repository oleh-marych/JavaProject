package com.oleg.command;

import com.oleg.command.util.Command;
import com.oleg.menu.ConsoleController;
import com.oleg.tariffUtil.CompanyTariffs;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class WriteToFileResultTest extends CommandTester {
    @BeforeAll
    public static void setUpCommand() {
        command = new WriteToFileResult("write", new ConsoleController(new HashMap<String, Command>(), new CompanyTariffs("Name")));
    }

    @Test
    public void writeWithNoArgsTest() {
        var expected = Optional.empty();

        assertEquals(expected, command.execute(company));
    }

    @Test
    public void writeDefaultTest() {
        assertEquals(Optional.empty(), command.execute(company, "default"));
    }

    @Test
    public void readFileNameTest() {
        assertEquals(Optional.empty(), command.execute(company, "filename default.txt"));
    }
}