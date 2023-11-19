package com.oleg.command;

import com.oleg.command.util.AbstractCommand;
import com.oleg.command.util.FileManager;
import com.oleg.command.util.SimpleCommand;
import com.oleg.tariff.BaseTariff;
import com.oleg.tariffUtil.CompanyTariffs;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class ReadFromFile extends AbstractCommand {
    private final FileManager fileManager = new FileManager();
    private String fileName;

    @Override
    public Optional<List<BaseTariff>> execute(CompanyTariffs company, String nextCommands) throws IllegalArgumentException {
        var listOfCommand = splitCommands(nextCommands);
        if (listOfCommand.size() == 2) {
            fileName = listOfCommand.getLast();
            nextCommands = listOfCommand.getFirst();
        }
        try {
            var res = commandMap.get(nextCommands).execute(company);
            logger.info("Виконалась команда " + name + " " + nextCommands);
            return res;
        }
        catch (NullPointerException e) {
            logger.error("No such command as " + nextCommands + " in "+ name +" statement");
            throw new IllegalArgumentException("\nNo such command as " + nextCommands + " in "+ name +" statement");
        }
    }

    public ReadFromFile(String name) {
        super(name, "зчитати тарифи компанії з вказаного файлу (працює з папкою: " + FileManager.dataInputDir + " )");
        logger = Logger.getLogger(ReadFromFile.class);
        commandMap.put("default", new SimpleCommand() {
            @Override
            public String getInfo(int tabulation) {
                return "\t".repeat(tabulation) + "default - зчитати з стандартного файл 'default.txt'";
            }

            @Override
            public Optional<List<BaseTariff>> execute(CompanyTariffs company) {
                fileManager.getDataFromFile(company, null);
                return Optional.of(company.getTariffList());
            }
        });
        commandMap.put("filename", new SimpleCommand() {
            @Override
            public String getInfo(int tabulation) {
                return "\t".repeat(tabulation) + "filename [file name] - зчитати з файлу '[file name]'";
            }

            @Override
            public Optional<List<BaseTariff>> execute(CompanyTariffs company) {
                fileManager.getDataFromFile(company, fileName);
                return Optional.of(company.getTariffList());
            }
        });
    }
}
