package com.qa.automation.pages;

import com.qa.automation.models.ProductData;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductsPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private static final String URL_PRODUCTOS =
        "https://automationexercise.com/products";

    private final By campoBusqueda =
        By.id("search_product");

    private final By botonBusqueda =
        By.id("submit_search");

    private final By tarjetasProductos =
        By.cssSelector(
            ".features_items .productinfo"
        );

    private final By modalCarrito =
        By.cssSelector("#cartModal .modal-content");

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
            driver,
            Duration.ofSeconds(15)
        );
    }

    // Abrir catálogo
    public void abrirPagina() {

        driver.get(URL_PRODUCTOS);

        wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                tarjetasProductos
            )
        );
    }

    // Ingresar nombre en buscador
    public void ingresarProducto(String nombreProducto) {

        WebElement campo = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                campoBusqueda
            )
        );

        campo.clear();
        campo.sendKeys(nombreProducto);
    }

    // Ejecutar búsqueda
    public void buscarProducto() {

        wait.until(
            ExpectedConditions.elementToBeClickable(
                botonBusqueda
            )
        ).click();
    }

    // Localizar producto por nombre
    private By localizarProducto(String nombreProducto) {

        return By.xpath(
            "//div[contains(concat(' ', normalize-space(@class), ' '), ' productinfo ')]" +
            "[./p[normalize-space()=" +
            xpathLiteral(nombreProducto) + "]]"
        );
    }

    // Verificar producto visible
    public boolean productoVisible(String nombreProducto) {

        List<WebElement> productos =
            driver.findElements(
                localizarProducto(nombreProducto)
            );

        for (WebElement producto : productos) {

            if (producto.isDisplayed()) {
                return true;
            }
        }

        return false;
    }

    // Obtener productos con ID, nombre y precio
    public List<ProductData> obtenerCatalogo() {

        abrirPagina();

        List<WebElement> tarjetas = wait.until(
            ExpectedConditions.visibilityOfAllElementsLocatedBy(
                tarjetasProductos
            )
        );

        List<ProductData> productos = new ArrayList<>();

        for (WebElement tarjeta : tarjetas) {

            WebElement nombreElemento =
                tarjeta.findElement(By.cssSelector("p"));

            WebElement precioElemento =
                tarjeta.findElement(By.cssSelector("h2"));

            WebElement botonAgregar =
                tarjeta.findElement(
                    By.cssSelector(
                        "a.add-to-cart[data-product-id]"
                    )
                );

            // Leer nombre directamente del HTML
            String nombre = nombreElemento.getDomProperty(
                "textContent"
            ).trim();

            String precio = precioElemento.getText().trim();

            String id = botonAgregar.getDomAttribute(
               "data-product-id"
            );

            if (id != null
                    && !id.isBlank()
                    && !nombre.isBlank()) {

                boolean repetido = productos.stream()
                    .anyMatch(p -> p.id().equals(id));

                if (!repetido) {
                    productos.add(
                        new ProductData(id, nombre, precio)
                    );
                }
            }
        }

        return productos;
    }

    // Obtener lista de nombres
    public List<String> obtenerProductosDisponibles() {

        List<String> nombres = new ArrayList<>();

        for (ProductData producto : obtenerCatalogo()) {
            nombres.add(producto.nombre());
        }

        return nombres;
    }

    // Seleccionar cantidad de productos
    public List<ProductData> seleccionarProductos(int cantidad) {

        if (cantidad <= 0) {
            throw new IllegalArgumentException(
                "La cantidad debe ser mayor que cero"
            );
        }

        List<ProductData> catalogo = obtenerCatalogo();

        if (catalogo.size() < cantidad) {
            throw new IllegalStateException(
                "Se solicitaron " + cantidad +
                " productos, pero solo existen " +
                catalogo.size()
            );
        }

        return new ArrayList<>(
            catalogo.subList(0, cantidad)
        );
    }

    // Agregar producto utilizando su ID real
    public void agregarProductoAlCarrito(ProductData producto) {

        System.out.println(
            "Agregando producto: " + producto.nombre()
        );

        By botonAgregar = By.cssSelector(
            "a.add-to-cart[data-product-id='" +
            producto.id() + "']"
        );

        WebElement boton = wait.until(
            ExpectedConditions.presenceOfElementLocated(
                botonAgregar
            )
        );

        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView({block: 'center'});",
            boton
        );

        wait.until(
            ExpectedConditions.elementToBeClickable(
                botonAgregar
            )
        ).click();

        System.out.println(
            "Clic realizado: " + producto.nombre()
        );

        try {

            new WebDriverWait(
                driver,
                Duration.ofSeconds(5)
            ).until(
                ExpectedConditions.visibilityOfElementLocated(
                    modalCarrito
                )
            );

            System.out.println(
                "Modal de confirmación visible"
            );

        } catch (TimeoutException e) {

            System.out.println(
                "Modal no visible. Se verificará el carrito."
            );
        }
    }

    // Mantener compatibilidad con pruebas anteriores
    public void agregarProductoAlCarrito(String nombreProducto) {

        By botonAgregar = By.xpath(
            "//div[contains(concat(' ', normalize-space(@class), ' '), ' productinfo ')]" +
            "[./p[normalize-space()=" +
            xpathLiteral(nombreProducto) + "]]" +
            "//a[contains(concat(' ', normalize-space(@class), ' '), ' add-to-cart ')]"
        );

        WebElement boton = wait.until(
            ExpectedConditions.elementToBeClickable(
                botonAgregar
            )
        );

        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView({block: 'center'});",
            boton
        );

        boton.click();

        try {
            new WebDriverWait(
                driver,
                Duration.ofSeconds(5)
            ).until(
                ExpectedConditions.visibilityOfElementLocated(
                    modalCarrito
                )
            );
        } catch (TimeoutException e) {
            System.out.println(
                "Modal no visible. Se verificará el carrito."
            );
        }
    }

    // XPath seguro
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
    
    // Comprobar que la sección de resultados se muestra
    public boolean seccionResultadosVisible() {

        By tituloResultados = By.xpath(
            "//h2[contains(@class,'title') " +
            "and normalize-space()='Searched Products']"
        );

        return wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                tituloResultados
            )
        ).isDisplayed();
    }

    // Contar los productos mostrados en los resultados
    public int contarProductosEncontrados() {

        By resultados = By.cssSelector(
            ".features_items .productinfo"
        );

        // Esperar a que termine la búsqueda
        wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.xpath(
                    "//h2[contains(@class,'title') " +
                    "and normalize-space()='Searched Products']"
                )
            )
        );

        return driver.findElements(resultados).size();
    }

public void continuarComprando() {

    By botonContinuar = By.cssSelector(
        "#cartModal button.close-modal"
    );

    // Esperar que el botón esté disponible
    WebElement boton = wait.until(
        ExpectedConditions.elementToBeClickable(
            botonContinuar
        )
    );

    // Cerrar ventana de confirmación
    boton.click();

    // Esperar que el modal desaparezca
    wait.until(
        ExpectedConditions.invisibilityOfElementLocated(
            modalCarrito
        )
    );

    System.out.println(
        "Ventana de confirmación cerrada"
    );
}

}
