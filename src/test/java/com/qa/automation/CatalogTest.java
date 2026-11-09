
package com.qa.automation;

import com.qa.automation.base.BaseTest;
import com.qa.automation.models.ProductData;
import com.qa.automation.pages.ProductsPage;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CatalogTest extends BaseTest {

    @Test
    public void validarLecturaCatalogo() {

        ProductsPage productsPage = new ProductsPage(driver);

        List<ProductData> productos =
            productsPage.seleccionarProductos(3);

        assertEquals(
            3,
            productos.size(),
            "Se deben obtener tres productos"
        );

        for (ProductData producto : productos) {

            assertFalse(producto.id().isBlank());
            assertFalse(producto.nombre().isBlank());
            assertFalse(producto.precio().isBlank());

            System.out.println(
                "ID: " + producto.id() +
                " | Nombre: " + producto.nombre() +
                " | Precio: " + producto.precio()
            );
        }

        System.out.println(
            "Catálogo leído correctamente"
        );
    }
}
