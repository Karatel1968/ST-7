package com.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class App {
    public static void main(String[] args) {
        String chromeDriverPath = System.getenv("CHROMEDRIVER_PATH");
        if (chromeDriverPath == null || chromeDriverPath.isBlank()) {
            chromeDriverPath = "C:/chromedriver/chromedriver.exe";
            System.out.println("Используется стандартный путь к ChromeDriver: " + chromeDriverPath);
            System.out.println("Если драйвер находится в другом месте, задайте переменную окружения CHROMEDRIVER_PATH");
        }

        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-gpu");
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36");
        
        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        
        try {
            System.out.println("Task 1: Генерация пароля...");
            driver.get("https://www.calculator.net/password-generator.html");
            
            wait.until(driver1 -> ((JavascriptExecutor) driver1).executeScript("return document.readyState").equals("complete"));
            
            WebElement passwordField = null;
            try {
                passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("final_password")));
            } catch (Exception e1) {
                System.out.println("Элемент по ID не найден, пробую другие селекторы...");
                try {
                    passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("final_password")));
                } catch (Exception e2) {
                    System.out.println("Элемент по name не найден, пробую CSS selector...");
                    passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[id*='password']")));
                }
            }
            
            String password = passwordField.getAttribute("value");
            System.out.println("Сгенерированный пароль: " + password);

            System.out.println("\nTask 2: Получение IP-адреса...");
            Task2.execute(driver, wait);
            
            System.out.println("\nTask 3: Прогноз погоды...");
            Task3.execute(driver, wait);

        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
