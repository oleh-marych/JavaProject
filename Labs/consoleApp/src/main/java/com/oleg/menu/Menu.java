package com.oleg.menu;

import com.oleg.command.*;
import com.oleg.command.util.AbstractCommand;
import com.oleg.command.util.Command;
import com.oleg.tariff.BaseTariff;
import com.oleg.tariffUtil.CompanyTariffs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.log4j.Logger;

public class Menu {
    private static  final Logger logger = Logger.getLogger(Menu.class);
    private final Map<String, Command> commands = new HashMap<>();
    public void start(){
        logger.info("Програма запустилась");
        ConsoleController consoleController = new ConsoleController(commands, new CompanyTariffs());
        initializeCommands(consoleController);

        while (true) {
            consoleController.getAndApplyCommand();
        }
    }

    private void initializeCommands(ConsoleController consoleController) {
        commands.put("print", new PrintData("print", consoleController));
        commands.put("count", new CountUsers("count"));
        commands.put("help", new HelpCommand("help", consoleController));
        commands.put("sort", new SortTariff("sort by"));
        commands.put("find", new FindTariff("find by"));
        commands.put("write-result", new WriteToFileResult("write-result", consoleController));
        commands.put("read-from", new ReadFromFile("read-from"));
        commands.put("exit", new AbstractCommand("exit", "вийти з програми") {
            @Override
            public Optional<List<BaseTariff>> execute(CompanyTariffs company) {
                Logger logger = Logger.getLogger("ExitCommand");
                logger.info("завершення програми");
                System.exit(0);
                return Optional.empty();
            }
        });
    }
}
