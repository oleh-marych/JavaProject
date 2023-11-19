package com.oleg.command;

import com.oleg.command.util.Command;
import com.oleg.menu.ConsoleController;
import com.oleg.tariffUtil.CompanyTariffs;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PrintDataTest extends CommandTester {
    @BeforeAll
    public static void setUpCommand() {
        command = new PrintData("sort", new ConsoleController(new HashMap<String, Command>(), new CompanyTariffs()));
    }

    @Test
    public void printAllTest() {
        var expected = company.getTariffList();

        assertEquals(expected, command.execute(company, "all").get());
    }

    @Test
    public void printWithNoArgsTest() {
        var expected = Optional.empty();

        assertEquals(expected, command.execute(company));
    }
}