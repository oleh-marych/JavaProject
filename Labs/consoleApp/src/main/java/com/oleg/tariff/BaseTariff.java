package com.oleg.tariff;

import java.util.Objects;

public class BaseTariff {
    public static final int UNLIMITED = -1;
    private int internetInGB = 0;
    private int callMinutes;
    private int kstSMS = 0;
    private String name;
    private double subscriptionFeePerMonth;
    private int amountOfUser = 0;

    public BaseTariff(String name, double subscriptionFeePerMonth, int amountOfUser, int callMinutes, int internetInGB, int kstSMS) {
        this.name = name;
        this.subscriptionFeePerMonth = subscriptionFeePerMonth;
        this.amountOfUser = amountOfUser;
        this.callMinutes = callMinutes;
        this.internetInGB = internetInGB;
        this.kstSMS = kstSMS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseTariff that = (BaseTariff) o;
        return Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return "Назва тарифу ' " + name + " ':" +
                "\n\tМобільний інтернет:\t  " + (internetInGB == UNLIMITED ? "Безлім" : "%d ГБ".formatted(internetInGB)) +
                "\n\tДзвінки:\t\t\t  " + (callMinutes == UNLIMITED ? "Безлім" : "%d хв".formatted(callMinutes)) +
                "\n\tКількість SMS:\t\t  " + (kstSMS == UNLIMITED ? "Безлім" : "%d шт".formatted(kstSMS)) +
                "\n\tПлата за тариф:\t\t  " + subscriptionFeePerMonth + " грн/міс" +
                "\n\tКількість абонентів:  " + amountOfUser;
    }

    public int getInternetInGB() {
        return internetInGB;
    }

    public int getCallMinutes() {
        return callMinutes;
    }

    public int getKstSMS() {
        return kstSMS;
    }

    public String getName() {
        return name;
    }

    public double getSubscriptionFeePerMonth() {
        return subscriptionFeePerMonth;
    }

    public int getAmountOfUser() {
        return amountOfUser;
    }
}
