package com.oleg.command;

import com.oleg.tariff.BaseTariff;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class FindTariffTest extends CommandTester {
    private final InputStream originalIn = System.in;

    @BeforeAll
    public static void setUpCommand() {
        command = new FindTariff("find");
    }

    @AfterEach
    public void reset() {
        System.setIn(originalIn);
    }

    @Test
    public void findByNameNotPresent() {
        String nameToFind = "Unknown tariff";
        var expected = Optional.empty();

        System.setIn(new ByteArrayInputStream(nameToFind.getBytes()));
        assertEquals(expected, command.execute(company, "by name"));
    }

    @Test
    public void findByName() {
        String nameToFind = "Lviv Plan";
        var expected = company.getTariffList().stream()
                .filter((e) -> Objects.equals(e.getName(), nameToFind)).toList().getFirst();

        System.setIn(new ByteArrayInputStream(nameToFind.getBytes()));
        assertEquals(expected, command.execute(company, "by name").get().getFirst());
    }

    @Test
    public void findByDiapasonTests() {
        int minFee = 120;
        int maxFee = 130;
        int minGB = 1;
        int maxGB = 3;
        int minMin = 140;
        int maxMin = 160;
        int minSMS = 5;
        int maxSMS = 10;
        String diapasonFee = "%d\n%d\n".formatted(minFee, maxFee);
        String diapasonGB = "%d\n%d\n".formatted(minGB, maxGB);
        String diapasonMin = "%d\n%d\n".formatted(minMin, maxMin);
        String diapasonSMS = "%d\n%d\n".formatted(minSMS, maxSMS);

        BaseTariff expected = new BaseTariff("Lviv+ Plan",120,3000,150,1,0);
        System.setIn(new ByteArrayInputStream(diapasonFee.getBytes()));
        assertEquals(expected, command.execute(company, "by diapason feePerMonth").get().getFirst());

        expected = new BaseTariff("Lviv Plan",100,2000,100,1,0);
        System.setIn(new ByteArrayInputStream(diapasonGB.getBytes()));
        assertEquals(expected, command.execute(company, "by diapason internetInGB").get().getFirst());

        expected = new BaseTariff("Lviv+ Plan",120,3000,150,1,0);
        System.setIn(new ByteArrayInputStream(diapasonMin.getBytes()));
        assertEquals(expected, command.execute(company, "by diapason callMinutes").get().getFirst());

        System.setIn(new ByteArrayInputStream(diapasonSMS.getBytes()));
        assertEquals(List.of(), command.execute(company, "by diapason kstSMS").get());
    }

    @Test
    public void findWithNoArgsTest() {
        var expected = Optional.empty();

        assertEquals(expected, command.execute(company));
    }


    @Test
    public void IllegalArgumentExceptionFindArgumentTest() {
        assertThrows(IllegalArgumentException.class, () -> command.execute(company, "by Unknown_command"));
    }

    @Test
    public void IllegalArgumentExceptionFindByDiapasonArgumentTest() {
        assertThrows(IllegalArgumentException.class, () -> command.execute(company, "by diapason Unknown_command"));
    }
}