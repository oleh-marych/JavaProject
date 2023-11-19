package com.oleg.command.util;

import com.oleg.tariff.BaseTariff;
import com.oleg.tariffUtil.CompanyTariffs;

import java.util.List;
import java.util.Optional;

public interface SimpleCommand {
    String getInfo(int tabulation);
    Optional<List<BaseTariff>> execute(CompanyTariffs company);
}
