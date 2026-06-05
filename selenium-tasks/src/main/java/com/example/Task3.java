package com.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Task3 {
    public static void execute(WebDriver driver, WebDriverWait wait) {
        String url = "https://api.open-meteo.com/v1/forecast" +
                "?latitude=56&longitude=44" +
                "&hourly=temperature_2m,rain" +
                "&timezone=Europe%2FMoscow" +
                "&forecast_days=1" +
                "&wind_speed_unit=ms";

        try {
            driver.get(url);
            String jsonText;
            try {
                WebElement pre = driver.findElement(By.tagName("pre"));
                jsonText = pre.getText();
            } catch (NoSuchElementException e) {
                jsonText = driver.findElement(By.tagName("body")).getText();
            }
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(jsonText);

            JSONObject hourly = (JSONObject) json.get("hourly");
            JSONArray times = (JSONArray) hourly.get("time");
            JSONArray temps = (JSONArray) hourly.get("temperature_2m");
            JSONArray rains = (JSONArray) hourly.get("rain");

            StringBuilder table = new StringBuilder();
            String header = String.format("%-4s %-20s %-15s %-10s%n", "№", "Дата/время", "Температура", "Осадки (мм)");
            table.append(header);
            table.append("-------------------------------------------------------------\n");
            System.out.print(header);
            System.out.print("-------------------------------------------------------------\n");

            for (int i = 0; i < times.size(); i++) {
                String line = String.format("%-4d %-20s %-15s %-10s%n",
                        i + 1,
                        times.get(i),
                        temps.get(i),
                        rains.get(i));
                table.append(line);
                System.out.print(line);
            }

            File resultDir = new File("result");
            if (!resultDir.exists()) {
                resultDir.mkdirs();
            }
            try (PrintWriter out = new PrintWriter(new FileWriter(new File(resultDir, "forecast.txt")))) {
                out.print(table.toString());
                System.out.println("Прогноз сохранён в result/forecast.txt");
            }
        } catch (Exception e) {
            System.out.println("Ошибка в Task3: " + e.getMessage());
            e.printStackTrace();
        }
    }
}