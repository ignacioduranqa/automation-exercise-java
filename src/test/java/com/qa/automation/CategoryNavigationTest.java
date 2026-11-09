
package com.qa.automation;

import com.qa.automation.base.BaseTest;
import com.qa.automation.pages.CategoriesPage;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CategoryNavigationTest extends BaseTest {

    @Test
    public void validarNavegacionCategorias() {

        CategoriesPage categoriesPage =
            new CategoriesPage(driver);

        // ======================================
        // CP-014 - ETAPA 1: ABRIR CATÁLOGO
        // ======================================

        categoriesPage.abrirPagina();

        int cantidadCategorias =
            categoriesPage.contarCategorias();

        assertTrue(
            cantidadCategorias > 0,
            "Deben existir categorías disponibles"
        );

        System.out.println(
            "CP-014 ETAPA 1 PASS: " +
            cantidadCategorias + " categorías encontradas"
        );

        // ======================================
        // CP-014 - ETAPA 2: SELECCIONAR CATEGORÍA
        // ======================================

        String categoria =
            categoriesPage.seleccionarCategoria();

        assertTrue(
            !categoria.isBlank(),
            "La categoría seleccionada no debe estar vacía"
        );

        System.out.println(
            "Categoría seleccionada: " + categoria
        );

        // ======================================
        // CP-014 - ETAPA 3: SUBCATEGORÍA
        // ======================================

        String subcategoria =
            categoriesPage.seleccionarSubcategoria();

        assertTrue(
            !subcategoria.isBlank(),
            "La subcategoría no debe estar vacía"
        );

        System.out.println(
            "Subcategoría seleccionada: " + subcategoria
        );

        // ======================================
        // CP-014 - ETAPA 4: VALIDAR RESULTADOS
        // ======================================

        String titulo =
            categoriesPage.obtenerTituloResultados();

        assertTrue(
            titulo.toLowerCase().contains(
                categoria.toLowerCase()
            ),
            "El título no contiene la categoría seleccionada"
        );

        assertTrue(
            titulo.toLowerCase().contains(
                subcategoria.toLowerCase()
            ),
            "El título no contiene la subcategoría seleccionada"
        );

        int cantidadProductos =
            categoriesPage.contarProductos();

        assertTrue(
            cantidadProductos > 0,
            "La categoría debe mostrar productos"
        );

        System.out.println(
            "CP-014 ETAPA 4 PASS: " +
            titulo + " | Productos: " + cantidadProductos
        );

        // ======================================
        // CP-014 - ETAPA 5: DETALLE DE PRODUCTO
        // ======================================

        categoriesPage.abrirPrimerProducto();

        assertTrue(
            categoriesPage.detalleProductoVisible(),
            "El detalle del producto debe mostrarse"
        );

        System.out.println(
            "CP-014 ETAPA 5 PASS: Detalle accesible"
        );

        System.out.println(
            "CP-014 COMPLETADO: Navegación correcta"
        );
    }
}
