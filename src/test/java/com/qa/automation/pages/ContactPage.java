
package com.qa.automation.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ContactPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private static final String URL_CONTACTO =
        "https://automationexercise.com/contact_us";

    private final By formulario =
        By.id("contact-us-form");

    private final By campoNombre =
        By.cssSelector("#contact-us-form input[name='name']");

    private final By campoCorreo =
        By.cssSelector("#contact-us-form input[data-qa='email']");

    private final By botonEnviar =
        By.cssSelector("#contact-us-form input[type='submit']");

    public ContactPage(WebDriver driver) {

        this.driver = driver;

        this.wait = new WebDriverWait(
            driver,
            Duration.ofSeconds(15)
        );
    }

    // Abrir página de contacto
    public void abrirPagina() {

        driver.get(URL_CONTACTO);

        wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                formulario
            )
        );
    }

    // Ingresar nombre
    public void ingresarNombre(String nombre) {

        WebElement campo = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                campoNombre
            )
        );

        campo.clear();
        campo.sendKeys(nombre);
    }

    // Ingresar correo
    public void ingresarCorreo(String correo) {

        WebElement campo = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                campoCorreo
            )
        );

        campo.clear();
        campo.sendKeys(correo);
    }

    // Presionar Submit
    public void presionarEnviar() {

        wait.until(
            ExpectedConditions.elementToBeClickable(
                botonEnviar
            )
        ).click();
    }

    // Validar correo mediante HTML5
    public boolean correoValido() {

        WebElement campo = driver.findElement(
            campoCorreo
        );

        return Boolean.TRUE.equals(
            ((JavascriptExecutor) driver).executeScript(
                "return arguments[0].checkValidity();",
                campo
            )
        );
    }

    // Comprobar que seguimos en el formulario
    public boolean formularioVisible() {

        return wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                formulario
            )
        ).isDisplayed();
    }
}
