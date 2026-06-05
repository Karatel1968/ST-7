package com.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class App {
    public static void main(String[] args) {
        boolean isCI = System.getenv("GITHUB_ACTIONS") != null;

        if (!isCI) {
            System.setProperty("webdriver.chrome.driver", "C:/chromedriver/chromedriver.exe");
        }
        ChromeOptions options = new ChromeOptions();
if (isCI) {
    options.addArguments("--headless=new"); 
    options.addArguments("--no-sandbox");
    options.addArguments("--disable-dev-shm-usage");
}

WebDriver webDriver = new ChromeDriver(options);
        
        try {
            // === ЗАДАНИЕ №1: Генератор паролей ===
            System.out.println("=== Задание №1 ===");
            webDriver.get("https://www.calculator.net/password-generator.html");

            Thread.sleep(2000);

            WebElement passwordElement;
            try {
                passwordElement = webDriver.findElement(By.className("verybigtext"));
            } catch (Exception e) {
                passwordElement = webDriver.findElement(By.cssSelector("#content b"));
            }

            String generatedPassword = passwordElement.getText();
            System.out.println("Сгенерированный пароль: " + generatedPassword);
            System.out.println();

            // === ЗАДАНИЕ №2 ===
            System.out.println("=== Задание №2 ===");
            Task2.run(webDriver);
            System.out.println();

            // === ЗАДАНИЕ №3 ===
            System.out.println("=== Задание №3 ===");
            Task3.run(webDriver);
            System.out.println();

        } catch (Exception e) {
            System.out.println("Произошла ошибка в главном потоке:");
            e.printStackTrace();
        } finally {
            webDriver.quit();
        }
    }
}
