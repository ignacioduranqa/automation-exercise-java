
package com.qa.automation.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutPage {

    private final WebDriverWait wait;

    // =====================================
    // ELEMENTOS DEL CHECKOUT
    // =====================================

    private final By botonCheckout =
        By.cssSelector("a.check_out");

    private final By modalCheckout =
        By.cssSelector("#checkoutModal .modal-content");

    private final By enlaceLogin =
        By.cssSelector("#checkoutModal a[href='/login']");

    private final By direccionEntrega =
        By.id("address_delivery");

    private final By direccionFacturacion =
        By.id("address_invoice");

    private final By resumenPedido =
        By.id("cart_info");

    private final By campoComentario =
        By.name("message");

    private final By botonRealizarPedido =
        By.cssSelector("a[href='/payment']");

    // =====================================
    // CONSTRUCTOR
    // =====================================

    public CheckoutPage(WebDriver driver) {

        this.wait = new WebDriverWait(
            driver,
            Duration.ofSeconds(15)
        );
    }

    // =====================================
    // CP-010: CHECKOUT SIN AUTENTICACIÓN
    // =====================================

    public void presionarCheckout() {

        wait.until(
            ExpectedConditions.elementToBeClickable(
                botonCheckout
            )
        ).click();
    }

    public boolean modalVisible() {

        return wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                modalCheckout
            )
        ).isDisplayed();
    }

    public boolean solicitaAutenticacion() {

        WebElement modal = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                modalCheckout
            )
        );

        return modal.getText().contains(
            "Register / Login account to proceed on checkout."
        );
    }

    public boolean enlaceLoginVisible() {

        return wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                enlaceLogin
            )
        ).isDisplayed();
    }

    // =====================================
    // CP-012: CHECKOUT AUTENTICADO
    // =====================================

    public void procederAlCheckout() {

        presionarCheckout();

        wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                direccionEntrega
            )
        );
    }

    public boolean checkoutVisible() {

        return wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                resumenPedido
            )
        ).isDisplayed();
    }

    // =====================================
    // DIRECCIONES
    // =====================================

    public String obtenerDireccionEntrega() {

        WebElement direccion = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                direccionEntrega
            )
        );

        return direccion.getText().trim();
    }

    public String obtenerDireccionFacturacion() {

        WebElement direccion = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                direccionFacturacion
            )
        );

        return direccion.getText().trim();
    }

    // =====================================
    // PRODUCTOS DINÁMICOS
    // =====================================

    private By localizarProducto(String idProducto) {

        return By.id("product-" + idProducto);
    }

    public boolean productoVisibleEnResumen(
            String idProducto) {

        return wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                localizarProducto(idProducto)
            )
        ).isDisplayed();
    }

    public String obtenerPrecioProducto(
            String idProducto) {

        WebElement fila = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                localizarProducto(idProducto)
            )
        );

        return fila.findElement(
            By.cssSelector(".cart_price p")
        ).getText().trim();
    }

    public int obtenerCantidadProducto(
            String idProducto) {

        WebElement fila = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                localizarProducto(idProducto)
            )
        );

        String cantidad = fila.findElement(
            By.cssSelector(".cart_quantity button")
        ).getText().trim();

        return Integer.parseInt(cantidad);
    }

    public String obtenerTotalProducto(
            String idProducto) {

        WebElement fila = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                localizarProducto(idProducto)
            )
        );

        return fila.findElement(
            By.cssSelector(".cart_total p")
        ).getText().trim();
    }

    // =====================================
    // ETAPA 5: REALIZAR PEDIDO
    // =====================================

    public void ingresarComentario(String comentario) {

        WebElement campo = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                campoComentario
            )
        );

        campo.clear();
        campo.sendKeys(comentario);
    }

    public void realizarPedido() {

        wait.until(
            ExpectedConditions.elementToBeClickable(
                botonRealizarPedido
            )
        ).click();
    }
}
