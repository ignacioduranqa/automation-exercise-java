
package com.qa.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Localizadores
    private final By emailInput =
        By.cssSelector("[data-qa='login-email']");

    private final By passwordInput =
        By.cssSelector("[data-qa='login-password']");

    private final By loginButton =
        By.cssSelector("[data-qa='login-button']");

    private final By errorMessage =
        By.xpath("//p[contains(text(),'Your email or password is incorrect!')]");

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
            driver, Duration.ofSeconds(10)
        );
    }

    // Abrir la página de login
    public void abrirLogin() {
        driver.get("https://automationexercise.com/login");
    }

    // Ingresar correo
    public void ingresarCorreo(String correo) {
        wait.until(
            ExpectedConditions.visibilityOfElementLocated(emailInput)
        ).sendKeys(correo);
    }

    // Ingresar contraseña
    public void ingresarPassword(String password) {
        wait.until(
            ExpectedConditions.visibilityOfElementLocated(passwordInput)
        ).sendKeys(password);
    }

    // Presionar Login
    public void presionarLogin() {
        wait.until(
            ExpectedConditions.elementToBeClickable(loginButton)
        ).click();
    }

    // Validar mensaje de error
    public boolean mensajeErrorVisible() {
        return wait.until(
            ExpectedConditions.visibilityOfElementLocated(errorMessage)
        ).isDisplayed();
    }
}
