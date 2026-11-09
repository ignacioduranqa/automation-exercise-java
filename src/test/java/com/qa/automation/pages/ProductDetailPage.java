
package com.qa.automation.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductDetailPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By nombreProducto =
        By.cssSelector(".product-information h2");

    private final By precioProducto =
        By.cssSelector(".product-information span span");

    private final By campoCantidad =
        By.id("quantity");

    private final By botonAgregar =
        By.cssSelector(".product-information button.cart");

    private final By modalCarrito =
        By.cssSelector("#cartModal .modal-content");

    public ProductDetailPage(WebDriver driver) {

        this.driver = driver;

        this.wait = new WebDriverWait(
            driver,
            Duration.ofSeconds(15)
        );
    }

    // Abrir el detalle de cualquier producto por ID
    public void abrirProducto(String idProducto) {

        driver.get(
            "https://automationexercise.com/product_details/"
            + idProducto
        );

        wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                nombreProducto
            )
        );
    }

    // Obtener nombre mostrado en el detalle
    public String obtenerNombre() {

        return wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                nombreProducto
            )
        ).getText().trim();
    }

    // Obtener precio mostrado en el detalle
    public String obtenerPrecio() {

        return wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                precioProducto
            )
        ).getText().trim();
    }

    // Establecer cantidad
    public void establecerCantidad(int cantidad) {

        if (cantidad <= 0) {
            throw new IllegalArgumentException(
                "La cantidad debe ser mayor que cero"
            );
        }

        WebElement campo = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                campoCantidad
            )
        );

        campo.clear();
        campo.sendKeys(String.valueOf(cantidad));
    }

    // Agregar producto al carrito
    public void agregarAlCarrito() {

        wait.until(
            ExpectedConditions.elementToBeClickable(
                botonAgregar
            )
        ).click();

        wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                modalCarrito
            )
        );
    }
}
