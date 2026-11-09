
package com.qa.automation;

import com.qa.automation.base.BaseTest;
import com.qa.automation.pages.LoginPage;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest extends BaseTest {

    @Test
    public void validarLoginIncorrecto() {

        // Crear el objeto de la página Login
        LoginPage loginPage = new LoginPage(driver);

        // Abrir la página de login
        loginPage.abrirLogin();

        // Ingresar credenciales incorrectas
        loginPage.ingresarCorreo("qa_invalido@example.com");
        loginPage.ingresarPassword("Password123Incorrecta");

        // Presionar el botón Login
        loginPage.presionarLogin();

        // Validar que aparezca el mensaje de error
        assertTrue(
            loginPage.mensajeErrorVisible(),
            "Debe aparecer el mensaje de credenciales incorrectas"
        );

        System.out.println("CP-002: Login incorrecto validado con POM");
    }
}
