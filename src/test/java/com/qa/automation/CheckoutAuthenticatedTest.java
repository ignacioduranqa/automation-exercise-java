
package com.qa.automation;

import com.qa.automation.base.BaseTest;
import com.qa.automation.models.ProductData;
import com.qa.automation.pages.SignupPage;
import com.qa.automation.pages.CartPage;
import com.qa.automation.pages.ProductsPage;
import com.qa.automation.pages.CheckoutPage;
import com.qa.automation.pages.PaymentPage;

import org.junit.jupiter.api.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckoutAuthenticatedTest extends BaseTest {

    @Test
    public void validarCheckoutUsuarioAutenticado() {

        SignupPage signupPage = new SignupPage(driver);
        ProductsPage productsPage = new ProductsPage(driver);
        CartPage cartPage = new CartPage(driver);
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        PaymentPage paymentPage = new PaymentPage(driver);

        WebDriverWait wait = new WebDriverWait(
            driver,
            Duration.ofSeconds(15)
        );

        // =====================================
        // ETAPA 1: REGISTRO Y AUTENTICACIÓN
        // =====================================

        String nombre = "QA Automation";

        String correo = "qa" +
            UUID.randomUUID().toString().replace("-", "") +
            "@example.com";

        String password = "QaTest12345!";

        signupPage.abrirPagina();

        signupPage.iniciarRegistro(nombre, correo);

        signupPage.completarFormulario(password);

        signupPage.crearCuenta();

        assertTrue(
            signupPage.cuentaCreadaCorrectamente(),
            "La cuenta debe crearse correctamente"
        );

        signupPage.continuar();

        By usuarioConectado = By.xpath(
            "//a[contains(.,'Logged in as')]"
        );

        assertTrue(
            wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                    usuarioConectado
                )
            ).isDisplayed(),
            "El usuario debe estar autenticado"
        );

        System.out.println(
            "CP-012 ETAPA 1 PASS: Registro y autenticación"
        );

        // =====================================
        // ETAPA 2: SELECCIÓN DINÁMICA
        // =====================================

        List<ProductData> productos =
            productsPage.seleccionarProductos(2);

        assertEquals(
            2,
            productos.size(),
            "Deben seleccionarse dos productos"
        );

        for (int i = 0; i < productos.size(); i++) {

            ProductData producto = productos.get(i);

            System.out.println(
                "Producto seleccionado: " +
                producto.nombre() +
                " | ID: " + producto.id() +
                " | Precio: " + producto.precio()
            );

            productsPage.agregarProductoAlCarrito(
                producto
            );

            if (i < productos.size() - 1) {
                productsPage.continuarComprando();
            }
        }

        cartPage.abrirCarrito();

        for (ProductData producto : productos) {

            assertTrue(
                cartPage.productoVisiblePorId(
                    producto.id()
                ),
                "Producto no encontrado: " +
                producto.nombre()
            );
        }

        System.out.println(
            "CP-012 ETAPA 2 PASS: Productos dinámicos validados"
        );

        // =====================================
        // ETAPA 3: PRECIOS Y CANTIDADES
        // =====================================

        int totalEsperadoPedido = 0;

        for (ProductData producto : productos) {

            validarPrecioYCantidad(
                cartPage,
                producto,
                1
            );

            totalEsperadoPedido +=
                convertirPrecioANumero(
                    producto.precio()
                );
        }

        System.out.println(
            "CP-012 ETAPA 3 PASS: Precios, cantidades y totales validados"
        );

        // =====================================
        // ETAPA 4: CHECKOUT
        // =====================================

        checkoutPage.procederAlCheckout();

        assertTrue(
            checkoutPage.checkoutVisible(),
            "El checkout debe mostrarse"
        );

        String direccionEntrega =
            checkoutPage.obtenerDireccionEntrega();

        String direccionFacturacion =
            checkoutPage.obtenerDireccionFacturacion();

        assertTrue(
            direccionEntrega.contains("Test Street 123"),
            "Dirección de entrega incorrecta"
        );

        assertTrue(
            direccionFacturacion.contains("Test Street 123"),
            "Dirección de facturación incorrecta"
        );

        int totalRealPedido = 0;

        for (ProductData producto : productos) {

            String id = producto.id();

            assertTrue(
                checkoutPage.productoVisibleEnResumen(id),
                "Producto no encontrado en checkout: " +
                producto.nombre()
            );

            int precioEsperado =
                convertirPrecioANumero(
                    producto.precio()
                );

            int precioReal =
                convertirPrecioANumero(
                    checkoutPage.obtenerPrecioProducto(id)
                );

            int cantidadReal =
                checkoutPage.obtenerCantidadProducto(id);

            int totalReal =
                convertirPrecioANumero(
                    checkoutPage.obtenerTotalProducto(id)
                );

            assertEquals(
                precioEsperado,
                precioReal,
                "Precio incorrecto: " +
                producto.nombre()
            );

            assertEquals(
                1,
                cantidadReal,
                "Cantidad incorrecta: " +
                producto.nombre()
            );

            assertEquals(
                precioEsperado * cantidadReal,
                totalReal,
                "Total incorrecto: " +
                producto.nombre()
            );

            totalRealPedido += totalReal;

            System.out.println(
                "PASS Checkout: " +
                producto.nombre() +
                " | Precio: " + precioReal +
                " | Cantidad: " + cantidadReal +
                " | Total: " + totalReal
            );
        }

        assertEquals(
            totalEsperadoPedido,
            totalRealPedido,
            "La suma de productos del checkout es incorrecta"
        );

        System.out.println(
            "PASS: Suma de productos del checkout: " +
            totalRealPedido
        );

        System.out.println(
            "CP-012 ETAPA 4 PASS: Checkout, direcciones y productos validados"
        );

        // =====================================
        // ETAPA 5: FINALIZAR COMPRA
        // =====================================

        checkoutPage.ingresarComentario(
            "Pedido de prueba automatizada QA"
        );

        checkoutPage.realizarPedido();

        paymentPage.completarFormularioPago(
            "QA Automation",
            "4111111111111111",
            "123",
            "12",
            "2030"
        );

        paymentPage.confirmarPago();

        assertTrue(
            paymentPage.compraConfirmada(),
            "La compra debe confirmarse correctamente"
        );

        System.out.println(
            "CP-012 ETAPA 5 PASS: Compra confirmada correctamente"
        );

        System.out.println(
            "CP-012 COMPLETADO: Flujo E2E de compra exitoso"
        );
    }

    // =====================================
    // CONVERTIR PRECIO A NÚMERO
    // =====================================

    private int convertirPrecioANumero(String precio) {

        String numero = precio.replaceAll(
            "[^0-9]",
            ""
        );

        return Integer.parseInt(numero);
    }

    // =====================================
    // VALIDAR PRECIO, CANTIDAD Y TOTAL
    // =====================================

    private void validarPrecioYCantidad(
            CartPage cartPage,
            ProductData producto,
            int cantidadEsperada) {

        String id = producto.id();

        int precioEsperado =
            convertirPrecioANumero(
                producto.precio()
            );

        int precioReal =
            convertirPrecioANumero(
                cartPage.obtenerPrecioPorId(id)
            );

        int cantidadReal =
            cartPage.obtenerCantidadPorId(id);

        int totalReal =
            convertirPrecioANumero(
                cartPage.obtenerTotalPorId(id)
            );

        assertEquals(
            precioEsperado,
            precioReal,
            "Precio incorrecto: " +
            producto.nombre()
        );

        assertEquals(
            cantidadEsperada,
            cantidadReal,
            "Cantidad incorrecta: " +
            producto.nombre()
        );

        assertEquals(
            precioEsperado * cantidadEsperada,
            totalReal,
            "Total incorrecto: " +
            producto.nombre()
        );

        System.out.println(
            "PASS: " +
            producto.nombre() +
            " | Precio: " + precioReal +
            " | Cantidad: " + cantidadReal +
            " | Total: " + totalReal
        );
    }
}
