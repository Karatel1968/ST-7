package com.example; // Укажи свой пакет, созданный Maven

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class App {
    public static void main(String[] args) {
        // Укажи реальный путь к твоему chromedriver
        System.setProperty("webdriver.chrome.driver", "C:/chromedriver/chromedriver.exe");
        
        WebDriver webDriver = new ChromeDriver();
        
        try {
            // === ЗАДАНИЕ №1: Генератор паролей ===
System.out.println("=== Задание №1 ===");
webDriver.get("https://www.calculator.net/password-generator.html");

// Добавим небольшую паузу (2 секунды), чтобы JS на странице точно отработал и сгенерировал пароль
Thread.sleep(2000);

// Самый надежный способ на calculator.net — вытащить текст из блока с классом 'verybigtext' 
// или текстового поля, где находится результат.
WebElement passwordElement;
try {
    // Пробуем найти по классу крупного текста (обычно пароль там)
    passwordElement = webDriver.findElement(By.className("verybigtext"));
} catch (Exception e) {
    // Резервный вариант: ищем любое жирное выделение в блоке результатов
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
            // Закрываем браузер после выполнения всех заданий
            webDriver.quit();
        }
    }
}
