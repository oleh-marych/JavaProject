package com.oleg.command;

import com.oleg.command.util.AbstractCommand;
import com.oleg.command.util.SimpleCommand;
import com.oleg.tariff.BaseTariff;
import com.oleg.tariffUtil.CompanyTariffs;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.stream.Stream;

public class FindTariff extends AbstractCommand {
    private String restCommands;
    public FindTariff(String name) {
        super(name, "знайти тарифи за деякими властивостями");
        logger = Logger.getLogger(FindTariff.class);
        commandMap.put("name", new SimpleCommand() {
            @Override
            public String getInfo(int tabulation) {
                return "\t".repeat(tabulation) + "name - знайти тариф за назвою";
            }

            @Override
            public Optional<List<BaseTariff>> execute(CompanyTariffs company) {
                Scanner sc = new Scanner(System.in);
                System.out.println("Введіть назву тарифу:\t");
                String nameToFound = sc.nextLine().trim();
                for (BaseTariff tariff: company.getTariffList()) {
                    if (Objects.equals(tariff.getName(), nameToFound)) {
                        List<BaseTariff> res = new ArrayList<>();
                        res.add(tariff);
                        return Optional.of(res);
                    }
                }
                System.out.println("Такого тарифу не знайдено");
                return Optional.empty();
            }
        });
        commandMap.put("diapason", new FindInDiapason("diapason"));
    }

    @Override
    public Optional<List<BaseTariff>> execute(CompanyTariffs company, String nextCommands) throws IllegalArgumentException {
        if (Objects.equals(nextCommands, "by")) {
            return super.execute(company);
        }
        restCommands = null;
        List<String> commands = splitCommands(nextCommands);
        String choice;
        if (commands.size() == 1) {
            throw new IllegalArgumentException("Можливо Ви мали на увазі \""+ name +"\"\n");
        }
        else if (commands.size() > 2) {
            restCommands = String.join(" ", commands.subList(2, commands.size()));
        }
        choice = commands.get(1);

        try {
            Optional<List<BaseTariff>> res = commandMap.get(choice).execute(company);
            logger.info("Виконалась команда " + name + " " + nextCommands);
            return res;
        }
        catch (NullPointerException e) {
            logger.error("No such command as " + nextCommands + " in "+ name +" statement");
            throw new IllegalArgumentException("\nNo such command as " + choice + " in "+ name +" statement");
        }
        catch (IllegalArgumentException e) {
            var errorTrace = e.getMessage().split(",");
            logger.error("No such command as " + errorTrace[1] + " in "+ name + " " + errorTrace[0] +" statement");
            throw new IllegalArgumentException("\nNo such command as " + errorTrace[1] + " in "+ name + " " + errorTrace[0] +" statement");
        }
    }

    private class FindInDiapason extends AbstractCommand {
        Stream<BaseTariff> temporaryResult;
        public FindInDiapason(String name) {
            super(name, "знайти тарифи у діапазоні заданих параметрів");
            commandMap.put("feePerMonth", new SimpleCommand() {
                @Override
                public String getInfo(int tabulation) {
                    return "\t".repeat(tabulation) + "feePerMonth - знайти тариф з абонплатою у заданій межі";
                }

                @Override
                public Optional<List<BaseTariff>> execute(CompanyTariffs company) {
                    Scanner sc = new Scanner(System.in);
                    System.out.println("Введіть мінімальну ціну тарифу:\t");
                    double minFee = sc.nextDouble();
                    System.out.println("Введіть максимальну ціну тарифу:\t");
                    double maxFee = sc.nextDouble();
                    temporaryResult = temporaryResult.filter((e) -> e.getSubscriptionFeePerMonth() >= minFee && e.getSubscriptionFeePerMonth() <= maxFee);
                    return Optional.empty();
                }
            });
            commandMap.put("internetInGB", new SimpleCommand() {
                @Override
                public String getInfo(int tabulation) {
                    return "\t".repeat(tabulation) + "internetInGB - знайти тариф з ГБ інтернету у заданій межі";
                }

                @Override
                public Optional<List<BaseTariff>> execute(CompanyTariffs company) {
                    Scanner sc = new Scanner(System.in);
                    System.out.println("Введіть мінімальну кількість ГБ інтернету у тарифу (-1 - знайти тарифи тільки з безлімом):\t");
                    int maxGB = -1, minGB = sc.nextInt();
                    if (minGB != -1) {
                        System.out.println("Введіть максимальну кількість ГБ інтернету у тарифу (-1 - для безліму, тобто без максимальної межі):\t");
                        maxGB = sc.nextInt();
                    }
                    if (maxGB == -1) {
                        temporaryResult = temporaryResult.filter((e) -> minGB == -1 ? e.getInternetInGB() == -1 : e.getInternetInGB() >= minGB);
                    }
                    else {
                        int finalMaxGB = maxGB;
                        temporaryResult = temporaryResult.filter((e) -> e.getInternetInGB() >= minGB && e.getInternetInGB() <= finalMaxGB);
                    }
                    return Optional.empty();
                }
            });
            commandMap.put("callMinutes", new SimpleCommand() {
                @Override
                public String getInfo(int tabulation) {
                    return "\t".repeat(tabulation) + "callMinutes - знайти тариф за кількістю хвилин в ньому";
                }

                @Override
                public Optional<List<BaseTariff>> execute(CompanyTariffs company) {
                    Scanner sc = new Scanner(System.in);
                    System.out.println("Введіть мінімальну кількість хвилин у тарифу (-1 - знайти тарифи тільки з безлімом):\t");
                    int max = -1, min = sc.nextInt();
                    if (min != -1) {
                        System.out.println("Введіть максимальну кількість хвилин у тарифу (-1 - для безліму, тобто без максимальної межі):\t");
                        max = sc.nextInt();
                    }
                    if (max == -1) {
                        temporaryResult = temporaryResult.filter((e) -> min == -1 ? e.getCallMinutes() == -1 : e.getCallMinutes() >= min);
                    }
                    else {
                        int finalMaxGB = max;
                        temporaryResult = temporaryResult.filter((e) -> e.getCallMinutes() >= min && e.getCallMinutes() <= finalMaxGB);
                    }
                    return Optional.empty();
                }
            });
            commandMap.put("kstSMS", new SimpleCommand() {
                @Override
                public String getInfo(int tabulation) {
                    return "\t".repeat(tabulation) + "kstSMS - знайти тариф за кількістю SMS в ньому";
                }

                @Override
                public Optional<List<BaseTariff>> execute(CompanyTariffs company) {
                    Scanner sc = new Scanner(System.in);
                    System.out.println("Введіть мінімальну кількість SMS у тарифу (-1 - знайти тарифи тільки з безлімом):\t");
                    int max = -1, min = sc.nextInt();
                    if (min != -1) {
                        System.out.println("Введіть максимальну кількість SMS у тарифу (-1 - для безліму, тобто без максимальної межі):\t");
                        max = sc.nextInt();
                    }
                    if (max == -1) {
                        temporaryResult = temporaryResult.filter((e) -> min == -1 ? e.getKstSMS() == -1 : e.getKstSMS() >= min);
                    }
                    else {
                        int finalMaxGB = max;
                        temporaryResult = temporaryResult.filter((e) -> e.getKstSMS() >= min && e.getKstSMS() <= finalMaxGB);
                    }
                    return Optional.empty();
                }
            });
        }

        @Override
        public Optional<List<BaseTariff>> execute(CompanyTariffs company) {
            if (restCommands == null) {
                System.out.println(getInfo(0));
                return Optional.empty();
            }

            var commands = parseCommand();
            temporaryResult = company.getTariffList().stream();
            Map<String, Boolean> usedCommands = new HashMap<>();

            for (String command: commands) {
                if (this.commandMap.get(command) == null) {
                    throw new IllegalArgumentException(name + "," + command);
                }
            }

            for (String command: commands) {
                if (usedCommands.get(command) == null) {
                    usedCommands.put(command, true);
                    this.commandMap.get(command).execute(company);
                }
            }

            return Optional.of(temporaryResult.toList());
        }

        private List<String> parseCommand() {
            return Arrays.stream(restCommands.split(",")).map(String::trim).toList();
        }
    }
}
