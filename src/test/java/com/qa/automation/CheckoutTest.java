
package com.qa.automation;

import java.util.List;

import com.qa.automation.base.BaseTest;
import com.qa.automation.models.ProductData;
import com.qa.automation.pages.ProductsPage;
import com.qa.automation.pages.CartPage;
import com.qa.automation.pages.CheckoutPage;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckoutTest extends BaseTest {

    @Test
    public void validarCheckoutUsuarioInvitado() {

        ProductsPage productsPage =
            new ProductsPage(driver);

        CartPage cartPage =
            new CartPage(driver);

        CheckoutPage checkoutPage =
            new CheckoutPage(driver);

        System.out.println(
            "CP-010: Iniciando flujo E2E"
        );

        // Obtener dos productos reales del catálogo
        List<ProductData> productos =
            productsPage.seleccionarProductos(2);

        // Agregar productos al carrito
        for (ProductData producto : productos) {

            System.out.println(
                "Agregando producto: " + producto.nombre()
            );

            productsPage.agregarProductoAlCarrito(
                producto.nombre()
            );

            // Cerrar modal antes del siguiente producto
            productsPage.continuarComprando();

            System.out.println(
                "Producto agregado: " + producto.nombre()
            );
        }

        // Abrir carrito
        cartPage.abrirCarrito();

        // Validar productos por ID
        for (ProductData producto : productos) {

            assertTrue(
                cartPage.productoVisiblePorId(
                    producto.id()
                ),
                "No aparece en el carrito: "
                    + producto.nombre()
            );

            System.out.println(
                "Producto validado: " + producto.nombre()
            );
        }

        // Intentar continuar al checkout
        checkoutPage.presionarCheckout();

        // Verificar modal de autenticación
        assertTrue(
            checkoutPage.modalVisible(),
            "No apareció el modal de checkout"
        );

        // Verificar mensaje de autenticación
        assertTrue(
            checkoutPage.solicitaAutenticacion(),
            "No se solicitó iniciar sesión"
        );

        // Verificar enlace Register / Login
        assertTrue(
            checkoutPage.enlaceLoginVisible(),
            "No aparece el enlace Register / Login"
        );

        System.out.println(
            "CP-010 PASS: Checkout requiere autenticación"
        );
    }
}
