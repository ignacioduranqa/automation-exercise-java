
package com.qa.automation;

import com.qa.automation.base.BaseTest;
import com.qa.automation.models.ProductData;
import com.qa.automation.pages.ProductsPage;
import com.qa.automation.pages.CartPage;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CartTest extends BaseTest {

    @Test
    public void validarProductosAgregadosAlCarrito() {

        ProductsPage productsPage = new ProductsPage(driver);
        CartPage cartPage = new CartPage(driver);

        // Obtener productos automáticamente del catálogo
        List<ProductData> productos =
            productsPage.seleccionarProductos(3);

        // Recorrer los productos seleccionados
        for (ProductData producto : productos) {

            System.out.println(
                "--------------------------------"
            );

            System.out.println(
                "CP-004: Probando producto: " +
                producto.nombre()
            );

            System.out.println(
                "ID: " + producto.id()
            );

            System.out.println(
                "Precio catálogo: " + producto.precio()
            );

            // Abrir catálogo
            productsPage.abrirPagina();

            // Agregar producto utilizando su ID
            productsPage.agregarProductoAlCarrito(producto);

            // Abrir carrito
            cartPage.abrirCarrito();

            // Validar que el producto esté agregado
            assertTrue(
                cartPage.productoVisiblePorId(
                    producto.id()
                ),
                "El producto con ID " +
                producto.id() +
                " no aparece en el carrito"
            );

            // Comparar precio del catálogo con el carrito
            String precioCarrito =
                cartPage.obtenerPrecioPorId(
                    producto.id()
                );

            assertEquals(
                producto.precio(),
                precioCarrito,
                "El precio del producto no coincide"
            );

            System.out.println(
                "CP-004 PASS: " + producto.nombre()
            );
        }

        System.out.println(
            "CP-004: Todos los productos fueron validados"
        );
    }
}
