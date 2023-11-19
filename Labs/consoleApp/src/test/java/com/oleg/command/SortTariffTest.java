package com.oleg.command;

import com.oleg.tariff.BaseTariff;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class SortTariffTest extends CommandTester {
    @BeforeAll
    public static void setUpCommand() {
        command = new SortTariff("sort");
    }

    @Test
    public void sortByFee() {
        var expected = company.getTariffList().stream()
                .sorted(Comparator.comparingDouble(BaseTariff::getSubscriptionFeePerMonth)).toList();

        assertEquals(expected, command.execute(company, "by feePerMonth").get());
    }

    @Test
    public void sortByInternetInGB() {
        var expected = company.getTariffList().stream()
                .sorted(Comparator.comparingDouble(BaseTariff::getInternetInGB)).toList();

        assertEquals(expected, command.execute(company, "by internetInGB").get());
    }

    @Test
    public void sortWithNoArgsTest() {
        var expected = Optional.empty();

        assertEquals(expected, command.execute(company));
    }

    @Test
    public void IllegalArgumentExceptionSortArgumentTest() {
        assertThrows(IllegalArgumentException.class, () -> command.execute(company, "by Unknown_command"));
    }
}