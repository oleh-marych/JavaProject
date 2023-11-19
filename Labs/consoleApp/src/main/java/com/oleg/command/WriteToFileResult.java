package com.oleg.command;

import com.oleg.command.util.AbstractCommand;
import com.oleg.command.util.FileManager;
import com.oleg.command.util.SimpleCommand;
import com.oleg.menu.ConsoleController;
import com.oleg.tariff.BaseTariff;
import com.oleg.tariffUtil.CompanyTariffs;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class WriteToFileResult extends AbstractCommand {
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
            commandMap.get(nextCommands).execute(company);
            logger.info("Виконалась команда " + name + " " + nextCommands);
        }
        catch (NullPointerException e) {
            logger.error("No such command as " + nextCommands + " in "+ name +" statement");
            throw new IllegalArgumentException("\nNo such command as " + nextCommands + " in "+ name +" statement");
        }
        return Optional.empty();
    }

    public WriteToFileResult(String name, ConsoleController consoleController) {
        super(name, "записати результат до вказаного файлу (працює з папкою: " + FileManager.dataOutputDir + " )");
        logger = Logger.getLogger(WriteToFileResult.class);
        commandMap.put("default", new SimpleCommand() {
            @Override
            public String getInfo(int tabulation) {
                return "\t".repeat(tabulation) + "default - записати у стандартний файл 'output.txt'";
            }

            @Override
            public Optional<List<BaseTariff>> execute(CompanyTariffs company) {
                fileManager.writeToFile(null, consoleController.getResult().orElse(List.of()));
                return Optional.empty();
            }
        });
        commandMap.put("filename", new SimpleCommand() {
            @Override
            public String getInfo(int tabulation) {
                return "\t".repeat(tabulation) + "filename [file name] - записати у файл '[file name]'";
            }

            @Override
            public Optional<List<BaseTariff>> execute(CompanyTariffs company) {
                fileManager.writeToFile(fileName, consoleController.getResult().orElse(List.of()));
                return Optional.empty();
            }
        });
    }
}
