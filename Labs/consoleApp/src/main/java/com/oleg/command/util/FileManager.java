package com.oleg.command.util;

import com.oleg.tariff.BaseTariff;
import com.oleg.tariffUtil.CompanyTariffs;

import java.io.*;
import java.util.List;

public class FileManager {
    public static final String currentDir = System.getProperty("user.dir");
    public static final String dataInputDir = currentDir + "\\src\\main\\resources\\data\\input";
    public static final String dataOutputDir = currentDir + "\\src\\main\\resources\\data\\output";
    public void getDataFromFile(CompanyTariffs company, String fileName) {
        try {
            if (fileName == null)
                fileName = "default.txt";
            File file = new File(dataInputDir + "\\" + fileName);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = bufferedReader.readLine();

            company.setCompanyName(line);

            while ((line = bufferedReader.readLine()) != null) {
                company.addNewTariff(new BaseTariff(line,
                        Double.parseDouble(bufferedReader.readLine()),
                        Integer.parseInt(bufferedReader.readLine()),
                        Integer.parseInt(bufferedReader.readLine()),
                        Integer.parseInt(bufferedReader.readLine()),
                        Integer.parseInt(bufferedReader.readLine())
                ));
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void writeToFile(String fileName, List<BaseTariff> result) {
        try {
            if (fileName == null)
                fileName = "output.txt";
            File file = new File(dataOutputDir + "\\" + fileName);
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for(BaseTariff tariff: result){
                bufferedWriter.write("\n");
                bufferedWriter.write(String.valueOf(tariff));
            }

            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
