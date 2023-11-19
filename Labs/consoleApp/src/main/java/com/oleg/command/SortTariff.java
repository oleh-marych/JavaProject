package com.oleg.command;

import com.oleg.command.util.AbstractCommand;
import com.oleg.command.util.SimpleCommand;
import com.oleg.tariff.BaseTariff;
import com.oleg.tariffUtil.CompanyTariffs;
import org.apache.log4j.Logger;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class SortTariff extends AbstractCommand {
    public SortTariff(String name) {
        super(name, "посортувати тарифи за деякою властивістю");
        logger = Logger.getLogger(SortTariff.class);
        commandMap.put("feePerMonth", new SimpleCommand() {
            @Override
            public String getInfo(int tabulation) {
                return "\t".repeat(tabulation) + "feePerMonth - посортувати всі тарифи за вартістю абонентської плати";
            }

            @Override
            public Optional<List<BaseTariff>> execute(CompanyTariffs company) {
                return Optional.of(company.getTariffList().stream().sorted(
                        Comparator.comparingDouble(BaseTariff::getSubscriptionFeePerMonth)
                ).toList());
            }
        });
        commandMap.put("internetInGB", new SimpleCommand() {
            @Override
            public String getInfo(int tabulation) {
                return "\t".repeat(tabulation) + "internetInGB - посортувати всі тарифи за вартістю кількістю ГБ інтернету";
            }

            @Override
            public Optional<List<BaseTariff>> execute(CompanyTariffs company) {
                return Optional.of(company.getTariffList().stream().sorted(
                        Comparator.comparingDouble(BaseTariff::getInternetInGB)
                ).toList());
            }
        });
    }

    @Override
    public Optional<List<BaseTariff>> execute(CompanyTariffs company, String nextCommands) throws IllegalArgumentException {
        if (Objects.equals(nextCommands, "by")) {
            return super.execute(company);
        }

        List<String> commands = splitCommands(nextCommands);
        if (commands.size() == 1) {
            throw new IllegalArgumentException("Можливо Ви мали на увазі \""+ name +"\"\n");
        }

        String choice = String.join(" ", commands.subList(1, commands.size()));

        try {
            var res = commandMap.get(choice).execute(company);
            logger.info("Виконалась команда " + name + " " + choice);
            return res;
        }
        catch (NullPointerException e) {
            logger.error("No such command as " + choice + " in "+ name +" statement");
            throw new IllegalArgumentException("\nNo such command as " + choice + " in "+ name +" statement");
        }
    }
}
