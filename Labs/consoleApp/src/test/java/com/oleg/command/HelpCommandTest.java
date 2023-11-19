package com.oleg.command;

import com.oleg.command.util.AbstractCommand;
import com.oleg.command.util.Command;
import com.oleg.command.util.FileManager;
import com.oleg.menu.ConsoleController;
import com.oleg.tariffUtil.CompanyTariffs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class HelpCommandTest {
    public FileManager fileManager = new FileManager();
    public CompanyTariffs company = new CompanyTariffs();
    public static AbstractCommand command = new HelpCommand("help", new ConsoleController(new HashMap<String, Command>(), new CompanyTariffs()));

    @BeforeEach
    public void setUp() {
        fileManager.getDataFromFile(company, null);
    }

    @Test
    public void printAllTest() {
        var expected = Optional.empty();

        assertEquals(expected, command.execute(company, "someText"));
        assertEquals(expected, command.execute(company));
    }
}