package com.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Task3 {
    public static void run(WebDriver webDriver) {
        String url = "https://api.open-meteo.com/v1/forecast?latitude=56&longitude=44&hourly=temperature_2m,rain&current=cloud_cover&timezone=Europe%2FMoscow&forecast_days=1&wind_speed_unit=ms";
        
        try {
            webDriver.get(url);
            WebElement elem = webDriver.findElement(By.tagName("pre"));
            String jsonStr = elem.getText();
            
            // Парсим структуру JSON
            JSONParser parser = new JSONParser();
            JSONObject root = (JSONObject) parser.parse(jsonStr);
            
            // Hourly данные лежат во вложенном объекте "hourly"
            JSONObject hourly = (JSONObject) root.get("hourly");
            
            JSONArray times = (JSONArray) hourly.get("time");
            JSONArray temperatures = (JSONArray) hourly.get("temperature_2m");
            JSONArray rains = (JSONArray) hourly.get("rain");
            
            // Строим таблицу в памяти с помощью StringBuilder
            StringBuilder tableBuilder = new StringBuilder();
            
            // Заголовок таблицы
            String header = String.format("%-4s | %-20s | %-15s | %-12s\n", "№", "Дата/время", "Температура", "Осадки (мм)");
            String underline = "-------------------------------------------------------------------\n";
            
            tableBuilder.append(header);
            tableBuilder.append(underline);
            
            // Заполняем строки данными (так как все массивы одинаковой длины — 24 часа)
            for (int i = 0; i < times.size(); i++) {
                String time = times.get(i).toString().replace("T", " "); // Делаем дату более читаемой
                double temp = Double.parseDouble(temperatures.get(i).toString());
                double rain = Double.parseDouble(rains.get(i).toString());
                
                String row = String.format("%-4d | %-20s | %-11.1f °C | %-12.2f\n", (i + 1), time, temp, rain);
                tableBuilder.append(row);
            }
            
            String finalTable = tableBuilder.toString();
            
            // Выводим в консоль
            System.out.println(finalTable);
            
            // Записываем в файл result/forecast.txt
            saveToFile(finalTable);
            
        } catch (Exception e) {
            System.out.println("Ошибка в Задании №3: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void saveToFile(String content) {
        // Создаем папку result, если её нет
        File dir = new File("result");
        if (!dir.exists()) {
            dir.mkdir();
        }
        
        File file = new File(dir, "forecast.txt");
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content);
            System.out.println("Прогноз успешно сохранен в файл: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Не удалось сохранить файл: " + e.getMessage());
        }
    }
}