package com.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.FileWriter;
import java.io.PrintWriter;

public class Task3 {
    public static void execute(WebDriver driver) {
        String url = "https://api.open-meteo.com/v1/forecast" +
                "?latitude=56&longitude=44" +
                "&hourly=temperature_2m,rain" +
                "&timezone=Europe%2FMoscow" +
                "&forecast_days=1" +
                "&wind_speed_unit=ms";

        try {
            driver.get(url);
            WebElement pre = driver.findElement(By.tagName("pre"));
            String jsonText = pre.getText();
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(jsonText);

            JSONObject hourly = (JSONObject) json.get("hourly");
            JSONArray times = (JSONArray) hourly.get("time");
            JSONArray temps = (JSONArray) hourly.get("temperature_2m");
            JSONArray rains = (JSONArray) hourly.get("rain");

            StringBuilder table = new StringBuilder();
            String header = String.format("%-4s %-20s %-15s %-10s%n", "№", "Дата/время", "Температура (°C)", "Осадки (мм)");
            table.append(header);
            System.out.print(header);

            for (int i = 0; i < times.size(); i++) {
                String line = String.format("%-4d %-20s %-15s %-10s%n",
                        i + 1,
                        times.get(i),
                        temps.get(i),
                        rains.get(i));
                table.append(line);
                System.out.print(line);
            }

            try (PrintWriter out = new PrintWriter(new FileWriter("result/forecast.txt"))) {
                out.print(table.toString());
                System.out.println("Прогноз сохранён в result/forecast.txt");
            }
        } catch (Exception e) {
            System.out.println("Ошибка в Task3: " + e.getMessage());
            e.printStackTrace();
        }
    }
}