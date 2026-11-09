
package com.qa.automation.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CategoriesPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private static final String URL_PRODUCTOS =
        "https://automationexercise.com/products";

    private final By categorias =
        By.cssSelector("#accordian .panel-heading a[data-toggle='collapse']");

    private final By tituloResultados =
        By.cssSelector(".features_items h2.title");

    private final By tarjetasProductos =
        By.cssSelector(".features_items .productinfo");

    public CategoriesPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
            driver, Duration.ofSeconds(15)
        );
    }

    // Abrir catálogo
    public void abrirPagina() {

        driver.get(URL_PRODUCTOS);

        wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                categorias
            )
        );
    }

    // Obtener categorías disponibles
    public int contarCategorias() {

        return driver.findElements(categorias).size();
    }

    // Seleccionar la primera categoría disponible
    public String seleccionarCategoria() {

        List<WebElement> listaCategorias =
            driver.findElements(categorias);

        if (listaCategorias.isEmpty()) {
            throw new IllegalStateException(
                "No se encontraron categorías"
            );
        }

        WebElement categoria = listaCategorias.get(0);

        String nombreCategoria =
            categoria.getText().trim();

        categoria.click();

        return nombreCategoria;
    }

    // Seleccionar una subcategoría de la categoría abierta
    public String seleccionarSubcategoria() {

        By subcategoriasVisibles = By.cssSelector(
            "#accordian .panel-collapse.in a"
        );

        WebElement subcategoria = wait.until(
            ExpectedConditions.elementToBeClickable(
                subcategoriasVisibles
            )
        );

        String nombreSubcategoria =
            subcategoria.getText().trim();

        subcategoria.click();

        wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                tituloResultados
            )
        );

        return nombreSubcategoria;
    }

    // Obtener título de resultados
    public String obtenerTituloResultados() {

        return wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                tituloResultados
            )
        ).getText().trim();
    }

    // Contar productos de la categoría seleccionada
    public int contarProductos() {

        wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                tarjetasProductos
            )
        );

        return driver.findElements(
            tarjetasProductos
        ).size();
    }

    // Abrir detalle del primer producto encontrado
    public void abrirPrimerProducto() {

        By enlacesDetalle = By.cssSelector(
            ".features_items a[href^='/product_details/']"
        );

        WebElement enlace = wait.until(
            ExpectedConditions.elementToBeClickable(
                enlacesDetalle
            )
        );

        enlace.click();

        wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".product-information h2")
            )
        );
    }

    // Validar que el detalle del producto se abrió
    public boolean detalleProductoVisible() {

        return wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".product-information h2")
            )
        ).isDisplayed();
    }
}
