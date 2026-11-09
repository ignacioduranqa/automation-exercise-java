
package com.qa.automation.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private static final String URL_CARRITO =
        "https://automationexercise.com/view_cart";

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
            driver,
            Duration.ofSeconds(15)
        );
    }

    // Abrir carrito de compras
    public void abrirCarrito() {

        driver.get(URL_CARRITO);

        wait.until(
            ExpectedConditions.presenceOfElementLocated(
                By.id("cart_info")
            )
        );
    }

    // Localizar cualquier producto utilizando su ID
    private By localizarProductoPorId(String idProducto) {

        return By.id("product-" + idProducto);
    }

    // Validar que un producto esté en el carrito
    public boolean productoVisiblePorId(String idProducto) {

        try {

            WebElement producto = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                    localizarProductoPorId(idProducto)
                )
            );

            return producto.isDisplayed();

        } catch (TimeoutException e) {

            return false;
        }
    }

    // Obtener precio unitario del producto
    public String obtenerPrecioPorId(String idProducto) {

        WebElement fila = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                localizarProductoPorId(idProducto)
            )
        );

        return fila.findElement(
            By.cssSelector(".cart_price p")
        ).getText().trim();
    }

    // Obtener cantidad del producto
    public int obtenerCantidadPorId(String idProducto) {

        WebElement fila = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                localizarProductoPorId(idProducto)
            )
        );

        String cantidad = fila.findElement(
            By.cssSelector(".cart_quantity button")
        ).getText().trim();

        return Integer.parseInt(cantidad);
    }

    // Obtener precio total del producto
    public String obtenerTotalPorId(String idProducto) {

        WebElement fila = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                localizarProductoPorId(idProducto)
            )
        );

        return fila.findElement(
            By.cssSelector(".cart_total p")
        ).getText().trim();
    }

    // Eliminar un producto utilizando su ID
    public void eliminarProductoPorId(String idProducto) {

        WebElement fila = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                localizarProductoPorId(idProducto)
            )
        );

        WebElement botonEliminar = fila.findElement(
            By.cssSelector(".cart_quantity_delete")
        );

        wait.until(
            ExpectedConditions.elementToBeClickable(
                botonEliminar
            )
        ).click();

        wait.until(
            ExpectedConditions.invisibilityOfElementLocated(
                localizarProductoPorId(idProducto)
            )
        );
    }

    // Comprobar que el producto fue eliminado
    public boolean productoEliminadoPorId(String idProducto) {

        return wait.until(
            ExpectedConditions.invisibilityOfElementLocated(
                localizarProductoPorId(idProducto)
            )
        );
    }

    // Compatibilidad con pruebas anteriores:
    // localizar producto por nombre
    private By localizarProductoPorNombre(String nombreProducto) {

        return By.xpath(
            "//tr[.//td[contains(@class,'cart_description')]" +
            "//a[normalize-space()=" +
            xpathLiteral(nombreProducto) + "]]"
        );
    }

    // Verificar producto utilizando su nombre
    public boolean productoVisibleEnCarrito(String nombreProducto) {

        try {

            return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                    localizarProductoPorNombre(nombreProducto)
                )
            ).isDisplayed();

        } catch (TimeoutException e) {

            return false;
        }
    }

    // Eliminar producto utilizando su nombre
    public void eliminarProductoDelCarrito(String nombreProducto) {

        WebElement fila = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                localizarProductoPorNombre(nombreProducto)
            )
        );

        fila.findElement(
            By.cssSelector(".cart_quantity_delete")
        ).click();

        wait.until(
            ExpectedConditions.invisibilityOfElementLocated(
                localizarProductoPorNombre(nombreProducto)
            )
        );
    }

    // Validar eliminación utilizando el nombre
    public boolean productoEliminado(String nombreProducto) {

        return wait.until(
            ExpectedConditions.invisibilityOfElementLocated(
                localizarProductoPorNombre(nombreProducto)
            )
        );
    }

    // Construir XPath seguro
    private String xpathLiteral(String texto) {

        if (!texto.contains("'")) {
            return "'" + texto + "'";
        }

        if (!texto.contains("\"")) {
            return "\"" + texto + "\"";
        }

        String[] partes = texto.split("'", -1);

        StringBuilder resultado =
            new StringBuilder("concat(");

        for (int i = 0; i < partes.length; i++) {

            if (i > 0) {
                resultado.append(", \"'\", ");
            }

            resultado.append("'")
                     .append(partes[i])
                     .append("'");
        }

        resultado.append(")");

        return resultado.toString();
    }
}
