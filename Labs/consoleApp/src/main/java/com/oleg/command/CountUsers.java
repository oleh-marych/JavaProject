package com.oleg.command;

import com.oleg.command.util.AbstractCommand;
import com.oleg.command.util.SimpleCommand;
import com.oleg.tariff.BaseTariff;
import com.oleg.tariffUtil.CompanyTariffs;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class CountUsers extends AbstractCommand {
    public CountUsers(String name) {
        super(name, "підраховує кількість абонентів компанії");
        logger = Logger.getLogger(CountUsers.class);
        commandMap.put("all", new SimpleCommand() {
            @Override
            public String getInfo(int tabulation) {
                return "\t".repeat(tabulation) + "all - підраховує усіх абонентів";
            }

            @Override
            public Optional<List<BaseTariff>> execute(CompanyTariffs company) {
                System.out.printf(" Кількість абонентів у компанії ' %s ' - %d\n", company.getCompanyName(),company.getAmountOfUser());
                return Optional.empty();
            }
        });
    }

    @Override
    public Optional<List<BaseTariff>> execute(CompanyTariffs company, String nextCommands) throws IllegalArgumentException {
        try {
            commandMap.get(nextCommands).execute(company);
            logger.info("Виконалась команда " + name + " " + nextCommands);
        }
        catch (NullPointerException e) {
            logger.error("No such command as " + nextCommands + " in "+ name +" statement");
            throw new IllegalArgumentException("\nNo such command as " + nextCommands + " in "+ name +" statement");
        }
        return Optional.empty();
    }
}
