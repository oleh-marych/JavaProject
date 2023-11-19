package com.oleg.command.util;

import com.oleg.menu.Menu;
import com.oleg.tariff.BaseTariff;
import com.oleg.tariffUtil.CompanyTariffs;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

import static com.oleg.menu.ConsoleController.TABULATION_SIZE;

public abstract class AbstractCommand implements Command, SimpleCommand {
    protected final String name;
    protected final String description;
    protected final Map<String, SimpleCommand> commandMap = new HashMap<>();
    protected Logger logger;

    public AbstractCommand(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String getInfo(int tabulation) {
        StringBuilder sb = new StringBuilder("\t".repeat(tabulation) + name + " - " + description);
        for (SimpleCommand c: commandMap.values()) {
            sb.append('\n').append(c.getInfo(tabulation + TABULATION_SIZE));
        }
        return sb.toString();
    }

    @Override
    public Optional<List<BaseTariff>> execute(CompanyTariffs company, String nextCommands) throws IllegalArgumentException {
        return Optional.empty();
    }

    @Override
    public Optional<List<BaseTariff>> execute(CompanyTariffs company)  {
        System.out.println(getInfo(0));
        return Optional.empty();
    }

    protected final List<String> splitCommands(String command) {
        return Arrays.stream(command.split(" ")).collect(Collectors.toCollection(LinkedList::new));
    }
}
