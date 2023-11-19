package com.oleg.command;

import com.oleg.command.util.AbstractCommand;
import com.oleg.menu.ConsoleController;
import com.oleg.tariff.BaseTariff;
import com.oleg.tariffUtil.CompanyTariffs;

import java.util.List;
import java.util.Optional;

public class HelpCommand extends AbstractCommand {
    private final ConsoleController consoleController;
    public HelpCommand(String name, ConsoleController consoleController) {
        super(name, "покаже усі команди");
        this.consoleController = consoleController;
    }

    @Override
    public Optional<List<BaseTariff>> execute(CompanyTariffs company) {
        consoleController.printCommand();
        return Optional.empty();
    }
}
