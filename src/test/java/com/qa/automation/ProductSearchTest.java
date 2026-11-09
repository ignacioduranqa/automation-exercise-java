
package com.qa.automation;

import com.qa.automation.base.BaseTest;
import com.qa.automation.pages.ProductsPage;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductSearchTest extends BaseTest {

    @Test
    public void validarBusquedaProducto() {

        // Crear objeto de la página de productos
        ProductsPage productsPage = new ProductsPage(driver);

        // Abrir la página de productos
        productsPage.abrirPagina();

        // Ingresar el producto que queremos buscar
        productsPage.ingresarProducto("Blue Top");

        // Presionar el botón de búsqueda
        productsPage.buscarProducto();

        // Validar que aparezca el producto buscado
        assertTrue(
            productsPage.productoVisible("Blue Top"),
            "El producto Blue Top debería aparecer en los resultados"
        );

        System.out.println(
            "CP-003: Búsqueda de producto validada correctamente"
        );
    }
}
