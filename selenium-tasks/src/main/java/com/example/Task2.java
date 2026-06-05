package com.example;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Task2 {
    public static void execute(WebDriver driver, WebDriverWait wait) {
        try {
            driver.get("https://api.ipify.org/?format=json");
            String jsonText;
            try {
                WebElement pre = driver.findElement(By.tagName("pre"));
                jsonText = pre.getText();
            } catch (NoSuchElementException e) {
                jsonText = driver.findElement(By.tagName("body")).getText();
            }
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(jsonText);
            String ip = (String) json.get("ip");
            System.out.println("Ваш IP-адрес: " + ip);
        } catch (Exception e) {
            System.out.println("Ошибка в Task2: " + e.getMessage());
            e.printStackTrace();
        }
    }
}