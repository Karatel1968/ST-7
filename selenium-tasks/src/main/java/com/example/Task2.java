package com.example;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Task2 {
    public static void execute(WebDriver driver) {
        try {
            driver.get("https://api.ipify.org/?format=json");
            WebElement pre = driver.findElement(By.tagName("pre"));
            String jsonText = pre.getText();
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(jsonText);
            String ip = (String) json.get("ip");
            System.out.println("Ваш IP-адрес: " + ip);
        } catch (Exception e) {
            System.out.println("Ошибка в Task2: " + e.getMessage());
        }
    }
}