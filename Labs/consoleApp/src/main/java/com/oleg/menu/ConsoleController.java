package com.oleg.menu;

import com.oleg.command.util.Command;
import com.oleg.tariff.BaseTariff;
import com.oleg.tariffUtil.CompanyTariffs;

import java.util.*;
import java.util.stream.Collectors;

public class ConsoleController {
    private final Map<String, Command> consoleCommands;
    public static final int TABULATION_SIZE = 2;
    private final CompanyTariffs companyTariffs;
    private final Scanner sc = new Scanner(System.in);
    private Optional<List<BaseTariff>> result = Optional.empty();

    public ConsoleController(Map<String, Command> commands, CompanyTariffs companyTariffs) {
        this.consoleCommands = commands;
        this.companyTariffs = companyTariffs;
    }

    public void getAndApplyCommand() {
        String consoleCommand = sc.nextLine().trim();
        if (consoleCommand.isEmpty()) return;
        List<String> commands = parseConsoleCommand(consoleCommand);

        try {
            if (commands.size() == 1) {
                result = consoleCommands.get(getGeneralCommand(commands)).execute(companyTariffs);
            } else if (commands.size() > 1) {
                result = consoleCommands.get(getGeneralCommand(commands)).execute(companyTariffs, getAdditionalCommands(commands));
            }
            result.ifPresent(ConsoleController::printResultArray);
        }
        catch (NullPointerException e) {
            System.out.println("\nNo such command as " + getGeneralCommand(commands) +
                                "\n\tWrite \"help\" to see the list of all commands\n");
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
    public void printCommand(){
        consoleCommands.forEach((key, value) -> System.out.println(value.getInfo(0)));
    }

    public static void printResultArray(List<BaseTariff> arr) {
        if (arr.size() == 0) {
            System.out.println("\t Результат запиту пустий ^_^");
        } else if (arr.size() == 1) {
            System.out.println("\t Результат запиту:");
            System.out.println(arr.getFirst());
        } else {
            System.out.println("\t Результат запиту:\n");
            for (int i = 0; i < arr.size(); i++) {
                System.out.printf("%2d)  ", i);
                System.out.println(arr.get(i));
            }
        }
    }
    public Optional<List<BaseTariff>> getResult() {
        return result;
    }
    public CompanyTariffs getCompanyTariffs() {
        return companyTariffs;
    }

    private String getAdditionalCommands(List<String> commands) {
        return String.join(" ", commands.subList(1, commands.size()));
    }

    private String getGeneralCommand(List<String> commands) {
        return commands.stream()
                .findFirst().orElse("");
    }

    private List<String> parseConsoleCommand(String consoleCommand) {
        return Arrays.stream(consoleCommand.split("\\s+")).collect(Collectors.toCollection(LinkedList::new));
    }
}
