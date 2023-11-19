package com.oleg.tariff;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BaseTariffTest {
    private final BaseTariff tariff = new BaseTariff("Tariff",100,2000,100,1,0);

    @Test
    void getName() {
        assertEquals(tariff.getName(), "Tariff");
    }

    @Test
    void getInternetInGB() {
        assertEquals(tariff.getInternetInGB(), 1);
    }

    @Test
    void getCallMinutes() {
        assertEquals(tariff.getCallMinutes(), 100);
    }

    @Test
    void getKstSMS() {
        assertEquals(tariff.getKstSMS(), 0);
    }

    @Test
    void getSubscriptionFeePerMonth() {
        assertEquals(tariff.getSubscriptionFeePerMonth(), 100);
    }

    @Test
    void getAmountOfUser() {
        assertEquals(tariff.getAmountOfUser(), 2000);
    }
}