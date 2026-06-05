package com.example;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Task2 {
    public static void run(WebDriver webDriver) {
        try {
            webDriver.get("https://api.ipify.org/?format=json");
            
            // Как и в примере с космонавтами, браузер отобразит JSON внутри тега <pre>
            WebElement elem = webDriver.findElement(By.tagName("pre"));
            String jsonStr = elem.getText();
            
            // Парсим JSON
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(jsonStr);
            
            // Извлекаем ключ "ip"
            String ip = (String) obj.get("ip");
            System.out.println("Ваш IPv4-адрес: " + ip);
            
        } catch (Exception e) {
            System.out.println("Ошибка в Задании №2: " + e.getMessage());
        }
    }
}