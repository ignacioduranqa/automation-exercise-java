
package com.qa.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class HomePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By enlaceProductos =
        By.cssSelector("a[href='/products']");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
            driver, Duration.ofSeconds(10)
        );
    }

    public void abrirPagina() {
        driver.get("https://automationexercise.com/");
    }

    public boolean enlaceProductosVisible() {
        return wait.until(
            ExpectedConditions.visibilityOfElementLocated(enlaceProductos)
        ).isDisplayed();
    }
}
