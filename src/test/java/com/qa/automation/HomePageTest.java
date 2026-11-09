
package com.qa.automation;

import com.qa.automation.base.BaseTest;
import com.qa.automation.pages.HomePage;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomePageTest extends BaseTest {

    @Test
    public void validarPaginaPrincipal() {

        HomePage homePage = new HomePage(driver);

        homePage.abrirPagina();

        assertTrue(
            homePage.enlaceProductosVisible(),
            "El enlace de productos debería estar visible"
        );

        System.out.println(
            "CP-001: Página principal validada con POM"
        );
    }
}
