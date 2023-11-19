package com.oleg.command;

import com.oleg.command.util.AbstractCommand;
import com.oleg.command.util.SimpleCommand;
import com.oleg.menu.ConsoleController;
import com.oleg.tariff.BaseTariff;
import com.oleg.tariffUtil.CompanyTariffs;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class PrintData extends AbstractCommand {

    public PrintData(String name, ConsoleController consoleController) {
        super(name, "виводить певну інформацію");
        logger = Logger.getLogger(PrintData.class);
        commandMap.put("all", new SimpleCommand() {
            @Override
            public String getInfo(int tabulation) {
                return "\t".repeat(tabulation) + "all - виводить інформацію про всі тарифи";
            }

            @Override
            public Optional<List<BaseTariff>> execute(CompanyTariffs company) {
                System.out.printf(" Усі тарифи компанії ' %s ':\n", company.getCompanyName());
                return Optional.of(company.getTariffList());
            }
        });
        commandMap.put("result", new SimpleCommand() {
            @Override
            public String getInfo(int tabulation) {
                return "\t".repeat(tabulation) + "result - виводить результат попереднього запиту";
            }

            @Override
            public Optional<List<BaseTariff>> execute(CompanyTariffs company) {
                return consoleController.getResult();
            }
        });
    }

    @Override
    public Optional<List<BaseTariff>> execute(CompanyTariffs company, String nextCommands) throws IllegalArgumentException {
        try {
            var res = commandMap.get(nextCommands).execute(company);
            logger.info("Виконалась команда " + name + " " + nextCommands);
            return res;
        }
        catch (NullPointerException e) {
            logger.error("\nNo such command as " + nextCommands + " in "+ name +" statement");
            throw new IllegalArgumentException("\nNo such command as " + nextCommands + " in "+ name +" statement");
        }
    }
}
