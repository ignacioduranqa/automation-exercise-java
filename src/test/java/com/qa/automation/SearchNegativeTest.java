package com.qa.automation;

import com.qa.automation.base.BaseTest;
import com.qa.automation.pages.ProductsPage;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchNegativeTest extends BaseTest {

    @Test
    public void validarBusquedaProductoInexistente() {

        ProductsPage productsPage = new ProductsPage(driver);

        // Producto que no existe en el catálogo
        String productoInexistente =
            "ProductoQAInexistente987654321";

        System.out.println(
            "CP-007: Iniciando búsqueda negativa"
        );

        // PASO 1: Abrir catálogo
        productsPage.abrirPagina();

        // PASO 2: Ingresar producto inexistente
        productsPage.ingresarProducto(
            productoInexistente
        );

        // PASO 3: Buscar producto
        productsPage.buscarProducto();

        // PASO 4: Validar sección de resultados
        assertTrue(
            productsPage.seccionResultadosVisible(),
            "No aparece la sección de resultados"
        );

        // PASO 5: Contar productos encontrados
        int cantidadResultados =
            productsPage.contarProductosEncontrados();

        System.out.println(
            "Productos encontrados: " + cantidadResultados
        );

        // PASO 6: Validar que no existan resultados
        assertEquals(
            0,
            cantidadResultados,
            "La búsqueda devolvió productos inesperados"
        );

        System.out.println(
            "CP-007 PASS: No se encontraron productos"
        );
    }
}
