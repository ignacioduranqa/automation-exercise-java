
package com.qa.automation.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PaymentPage {

    private final WebDriverWait wait;

    // =====================================
    // ELEMENTOS DEL FORMULARIO DE PAGO
    // =====================================

    private final By nombreTarjeta =
        By.name("name_on_card");

    private final By numeroTarjeta =
        By.name("card_number");

    private final By codigoSeguridad =
        By.name("cvc");

    private final By mesExpiracion =
        By.name("expiry_month");

    private final By anioExpiracion =
        By.name("expiry_year");

    private final By botonConfirmarPago =
        By.id("submit");

    private final By mensajeConfirmacion =
        By.cssSelector(
            "[data-qa='order-placed']"
        );

    // =====================================
    // CONSTRUCTOR
    // =====================================

    public PaymentPage(WebDriver driver) {

        this.wait = new WebDriverWait(
            driver,
            Duration.ofSeconds(15)
        );
    }

    // =====================================
    // COMPLETAR FORMULARIO
    // =====================================

    public void completarFormularioPago(
            String nombre,
            String numero,
            String cvc,
            String mes,
            String anio) {

        WebElement campoNombre = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                nombreTarjeta
            )
        );

        campoNombre.sendKeys(nombre);

        wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                numeroTarjeta
            )
        ).sendKeys(numero);

        wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                codigoSeguridad
            )
        ).sendKeys(cvc);

        wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                mesExpiracion
            )
        ).sendKeys(mes);

        wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                anioExpiracion
            )
        ).sendKeys(anio);
    }

    // =====================================
    // CONFIRMAR PAGO
    // =====================================

    public void confirmarPago() {

        wait.until(
            ExpectedConditions.elementToBeClickable(
                botonConfirmarPago
            )
        ).click();
    }

    // =====================================
    // VALIDAR COMPRA
    // =====================================

    public boolean compraConfirmada() {

        return wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                mensajeConfirmacion
            )
        ).isDisplayed();
    }
}
