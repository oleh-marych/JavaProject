package com.oleg.tariffUtil;

import com.oleg.tariff.BaseTariff;

import java.util.LinkedList;
import java.util.List;

public class CompanyTariffs {
    private String companyName;
    private final List<BaseTariff> tariffList = new LinkedList<>();

    public CompanyTariffs() {}
    public CompanyTariffs(String companyName) {
        this.companyName = companyName;
    }

    public void addNewTariff(BaseTariff newTariff) {
        if (!tariffList.contains(newTariff)) {
            tariffList.add(newTariff);
        }
        else {
            updateTariff(newTariff);
        }
    }
    public void updateTariff(BaseTariff newTariffValue) {
        int oldTariffValueIndex = tariffList.indexOf(newTariffValue);
        if (oldTariffValueIndex != -1) {
            tariffList.set(oldTariffValueIndex, newTariffValue);
        }
    }
    public int getAmountOfUser() {
        int kst = 0;
        for (BaseTariff tariff: tariffList) {kst += tariff.getAmountOfUser();}
        return kst;
    }

    public List<BaseTariff> getTariffList() {
        return tariffList;
    }
    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
