package com.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class App {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:/chromedriver/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        try {
            driver.get("https://www.calculator.net/password-generator.html");
            WebElement passwordField = driver.findElement(By.id("final_password"));
            String password = passwordField.getAttribute("value");
            System.out.println("Сгенерированный пароль: " + password);

            Task2.execute(driver);

            Task3.execute(driver);

        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
