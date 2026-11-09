
package com.qa.automation.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;

import java.util.List;

public class AdHandler {

    private final WebDriver driver;

    public AdHandler(WebDriver driver) {
        this.driver = driver;
    }

    // Intentar cerrar anuncios que tengan un botón de cierre
    public void cerrarAnuncios() {

        // Volver al documento principal
        driver.switchTo().defaultContent();

        // Localizadores de botones de cierre conocidos
        By[] botonesCerrar = {
            By.cssSelector("[aria-label='Close ad']"),
            By.cssSelector("[aria-label='Close Ad']"),
            By.cssSelector("[aria-label='Close advertisement']")
        };

        for (By localizador : botonesCerrar) {

            List<WebElement> botones =
                driver.findElements(localizador);

            for (WebElement boton : botones) {

                try {
                    if (boton.isDisplayed() && boton.isEnabled()) {
                        boton.click();
                    }
                } catch (org.openqa.selenium.WebDriverException e) {
                    // El anuncio pudo desaparecer antes del clic
                }
            }
        }
    }

    // Desplazar un elemento al centro de la pantalla
    public void centrarElemento(WebElement elemento) {

        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView({block: 'center'});",
            elemento
        );
    }
}
