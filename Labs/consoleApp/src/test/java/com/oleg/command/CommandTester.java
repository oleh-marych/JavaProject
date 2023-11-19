package com.oleg.command;

import com.oleg.command.util.AbstractCommand;
import com.oleg.command.util.FileManager;
import com.oleg.tariffUtil.CompanyTariffs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public abstract class CommandTester {
    public FileManager fileManager = new FileManager();
    public CompanyTariffs company = new CompanyTariffs();
    public static AbstractCommand command;
    @BeforeEach
    public void setUp() {
        fileManager.getDataFromFile(company, null);
    }

    @Test
    public void IllegalArgumentExceptionCommandTest() {
        assertThrows(IllegalArgumentException.class, () -> command.execute(company, "Unknown_command"));
    }
}
