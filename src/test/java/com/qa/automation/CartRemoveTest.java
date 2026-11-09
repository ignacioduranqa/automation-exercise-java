
package com.qa.automation;

import com.qa.automation.base.BaseTest;
import com.qa.automation.models.ProductData;
import com.qa.automation.pages.ProductsPage;
import com.qa.automation.pages.CartPage;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CartRemoveTest extends BaseTest {

    @Test
    public void validarEliminarProductosDelCarrito() {

        ProductsPage productsPage = new ProductsPage(driver);
        CartPage cartPage = new CartPage(driver);

        // Obtener tres productos automáticamente
        List<ProductData> productos =
            productsPage.seleccionarProductos(3);

        // Recorrer los productos seleccionados
        for (ProductData producto : productos) {

            System.out.println(
                "--------------------------------"
            );

            System.out.println(
                "CP-005: Probando producto: " +
                producto.nombre()
            );

            System.out.println(
                "ID: " + producto.id()
            );

            // PASO 1: Abrir catálogo
            productsPage.abrirPagina();

            // PASO 2: Agregar producto al carrito
            productsPage.agregarProductoAlCarrito(producto);

            // PASO 3: Abrir carrito
            cartPage.abrirCarrito();

            // PASO 4: Verificar que está agregado
            assertTrue(
                cartPage.productoVisiblePorId(
                    producto.id()
                ),
                "El producto no fue agregado: " +
                producto.id()
            );

            System.out.println(
                "Producto encontrado en el carrito"
            );

            // PASO 5: Eliminar producto
            cartPage.eliminarProductoPorId(
                producto.id()
            );

            System.out.println(
                "Producto eliminado"
            );

            // PASO 6: Verificar que desapareció
            assertTrue(
                cartPage.productoEliminadoPorId(
                    producto.id()
                ),
                "El producto sigue en el carrito: " +
                producto.id()
            );

            // Comprobación adicional
            assertFalse(
                cartPage.productoVisiblePorId(
                    producto.id()
                ),
                "El producto continúa visible: " +
                producto.id()
            );

            System.out.println(
                "CP-005 PASS: " + producto.nombre()
            );
        }

        System.out.println(
            "CP-005: Todos los productos fueron eliminados correctamente"
        );
    }
}
