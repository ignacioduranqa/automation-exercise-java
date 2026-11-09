
package com.qa.automation;

import com.qa.automation.base.BaseTest;
import com.qa.automation.models.ProductData;
import com.qa.automation.pages.ProductsPage;
import com.qa.automation.pages.CartPage;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CartPriceTest extends BaseTest {

    @Test
    public void validarPrecioCantidadYTotal() {

        ProductsPage productsPage = new ProductsPage(driver);
        CartPage cartPage = new CartPage(driver);

        // Obtener tres productos automáticamente
        List<ProductData> productos =
            productsPage.seleccionarProductos(3);

        for (ProductData producto : productos) {

            System.out.println("-----------------------------");
            System.out.println(
                "CP-006: Validando " + producto.nombre()
            );

            // Abrir catálogo
            productsPage.abrirPagina();

            // Agregar producto
            productsPage.agregarProductoAlCarrito(producto);

            // Abrir carrito
            cartPage.abrirCarrito();

            // Verificar presencia del producto
            assertTrue(
                cartPage.productoVisiblePorId(producto.id()),
                "El producto no aparece en el carrito"
            );

            // Obtener datos del carrito
            String precioTexto =
                cartPage.obtenerPrecioPorId(producto.id());

            int cantidad =
                cartPage.obtenerCantidadPorId(producto.id());

            String totalTexto =
                cartPage.obtenerTotalPorId(producto.id());

            // Convertir precios a números
            int precioUnitario =
                convertirPrecio(precioTexto);

            int total =
                convertirPrecio(totalTexto);

            // Calcular el total esperado
            int totalEsperado = precioUnitario * cantidad;

            System.out.println(
                "Precio unitario: " + precioUnitario
            );

            System.out.println(
                "Cantidad: " + cantidad
            );

            System.out.println(
                "Total esperado: " + totalEsperado
            );

            System.out.println(
                "Total obtenido: " + total
            );

            // Validar precio contra el catálogo
            assertEquals(
                convertirPrecio(producto.precio()),
                precioUnitario,
                "El precio no coincide con el catálogo"
            );

            // Validar cantidad
            assertTrue(
                cantidad > 0,
                "La cantidad debe ser mayor que cero"
            );

            // Validar cálculo del total
            assertEquals(
                totalEsperado,
                total,
                "El total del carrito es incorrecto"
            );

            System.out.println(
                "CP-006 PASS: " + producto.nombre()
            );
        }

        System.out.println(
            "CP-006: Todos los precios y totales fueron validados"
        );
    }

    // Convertir "Rs. 500" a 500
    private int convertirPrecio(String precio) {

        String numero = precio.replaceAll("[^0-9]", "");

        if (numero.isEmpty()) {
            throw new IllegalArgumentException(
                "No se pudo convertir el precio: " + precio
            );
        }

        return Integer.parseInt(numero);
    }
}
