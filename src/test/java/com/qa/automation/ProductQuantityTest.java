
package com.qa.automation;

import com.qa.automation.base.BaseTest;
import com.qa.automation.models.ProductData;
import com.qa.automation.pages.ProductsPage;
import com.qa.automation.pages.ProductDetailPage;
import com.qa.automation.pages.CartPage;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductQuantityTest extends BaseTest {

    @Test
    public void validarCantidadYTotalProducto() {

        ProductsPage productsPage =
            new ProductsPage(driver);

        ProductDetailPage detailPage =
            new ProductDetailPage(driver);

        CartPage cartPage =
            new CartPage(driver);

        // =====================================
        // CP-013 - ETAPA 1: PRODUCTO DINÁMICO
        // =====================================

        List<ProductData> productos =
            productsPage.seleccionarProductos(1);

        ProductData producto = productos.get(0);

        System.out.println(
            "Producto seleccionado: " +
            producto.nombre() +
            " | ID: " + producto.id() +
            " | Precio: " + producto.precio()
        );

        // =====================================
        // CP-013 - ETAPA 2: DETALLE DEL PRODUCTO
        // =====================================

        detailPage.abrirProducto(
            producto.id()
        );

        assertEquals(
            producto.nombre(),
            detailPage.obtenerNombre(),
            "El nombre del producto no coincide"
        );

        assertEquals(
            convertirPrecioANumero(producto.precio()),
            convertirPrecioANumero(
                detailPage.obtenerPrecio()
            ),
            "El precio del detalle no coincide"
        );

        System.out.println(
            "CP-013 ETAPA 2 PASS: Detalle validado"
        );

        // =====================================
        // CP-013 - ETAPA 3: MODIFICAR CANTIDAD
        // =====================================

        int cantidadEsperada = 4;

        detailPage.establecerCantidad(
            cantidadEsperada
        );

        detailPage.agregarAlCarrito();

        System.out.println(
            "CP-013 ETAPA 3 PASS: Cantidad configurada"
        );

        // =====================================
        // CP-013 - ETAPA 4: VALIDAR CARRITO
        // =====================================

        cartPage.abrirCarrito();

        assertTrue(
            cartPage.productoVisiblePorId(
                producto.id()
            ),
            "El producto debe aparecer en el carrito"
        );

        int precioEsperado =
            convertirPrecioANumero(
                producto.precio()
            );

        int precioReal =
            convertirPrecioANumero(
                cartPage.obtenerPrecioPorId(
                    producto.id()
                )
            );

        int cantidadReal =
            cartPage.obtenerCantidadPorId(
                producto.id()
            );

        int totalReal =
            convertirPrecioANumero(
                cartPage.obtenerTotalPorId(
                    producto.id()
                )
            );

        assertEquals(
            precioEsperado,
            precioReal,
            "El precio unitario es incorrecto"
        );

        assertEquals(
            cantidadEsperada,
            cantidadReal,
            "La cantidad del carrito es incorrecta"
        );

        assertEquals(
            precioEsperado * cantidadEsperada,
            totalReal,
            "El total del carrito es incorrecto"
        );

        System.out.println(
            "CP-013 ETAPA 4 PASS: Carrito validado"
        );

        System.out.println(
            "Producto: " + producto.nombre() +
            " | Precio: " + precioReal +
            " | Cantidad: " + cantidadReal +
            " | Total: " + totalReal
        );

        System.out.println(
            "CP-013 COMPLETADO: Cantidad y total correctos"
        );
    }

    // Convertir "Rs. 500" a 500
    private int convertirPrecioANumero(String precio) {

        return Integer.parseInt(
            precio.replaceAll("[^0-9]", "")
        );
    }
}
