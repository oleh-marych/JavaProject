package com.oleg.tariffUtil;

import com.oleg.command.util.FileManager;
import com.oleg.tariff.BaseTariff;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompanyTariffsTest {
    FileManager fileManager = new FileManager();
    CompanyTariffs company = new CompanyTariffs();

    @BeforeEach
    void setUp() {
        fileManager.getDataFromFile(company, null);
    }

    @Test
    public void updateTariffTest() {
        BaseTariff tariff = new BaseTariff("Lviv Plan",900,2000,100,1,0);
        company.updateTariff(tariff);
        int index = company.getTariffList().indexOf(tariff);
        assertEquals(company.getTariffList().get(index).getName(), "Lviv Plan");
        assertEquals(company.getTariffList().get(index).getSubscriptionFeePerMonth(), 900);
    }

    @Test
    public void addTariffTest() {
        BaseTariff tariff = new BaseTariff("New",900,2000,100,1,0);
        company.addNewTariff(tariff);
        int index = company.getTariffList().indexOf(tariff);
        assertEquals(company.getTariffList().get(index).getName(), "New");
    }

    @Test
    public void addAndUpdateIfPresentTariffTest() {
        BaseTariff tariff = new BaseTariff("Lviv Plan",900,2000,100,1,0);
        company.addNewTariff(tariff);
        int index = company.getTariffList().indexOf(tariff);
        assertEquals(company.getTariffList().get(index).getName(), "Lviv Plan");
        assertEquals(company.getTariffList().get(index).getSubscriptionFeePerMonth(), 900);
    }

    @Test
    void getAmountOfUser() {
        assertEquals(9000, company.getAmountOfUser());
    }

    @Test
    void getCompanyName() {
        assertEquals("LvivStar", company.getCompanyName());
    }

    @Test
    void setCompanyName() {
        company.setCompanyName("Changed");
        assertEquals("Changed", company.getCompanyName());
    }
}