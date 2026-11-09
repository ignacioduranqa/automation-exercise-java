
package com.qa.automation;

import com.qa.automation.base.BaseTest;
import com.qa.automation.pages.SignupPage;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SignupValidationTest extends BaseTest {

    @Test
    public void validarCamposObligatoriosRegistro() {

        SignupPage signupPage = new SignupPage(driver);

        System.out.println(
            "CP-008: Validando campos obligatorios"
        );

        // Abrir formulario
        signupPage.abrirPagina();

        // ESCENARIO 1: Nombre vacío
        signupPage.ingresarNombre("");
        signupPage.ingresarCorreo("qa.prueba@example.com");

        signupPage.presionarSignup();

        assertFalse(
            signupPage.nombreValido(),
            "El nombre vacío no debería ser válido"
        );

        assertTrue(
            signupPage.formularioVisible(),
            "El formulario no debería avanzar"
        );

        System.out.println(
            "PASS: Nombre obligatorio validado"
        );

        // ESCENARIO 2: Correo vacío
        signupPage.ingresarNombre("Usuario QA");
        signupPage.ingresarCorreo("");

        signupPage.presionarSignup();

        assertFalse(
            signupPage.correoValido(),
            "El correo vacío no debería ser válido"
        );

        assertTrue(
            signupPage.formularioVisible(),
            "El formulario no debería avanzar"
        );

        System.out.println(
            "PASS: Correo obligatorio validado"
        );

        // ESCENARIO 3: Correo con formato inválido
        signupPage.ingresarNombre("Usuario QA");
        signupPage.ingresarCorreo("correo-invalido");

        signupPage.presionarSignup();

        assertFalse(
            signupPage.correoValido(),
            "El correo inválido no debería ser aceptado"
        );

        assertTrue(
            signupPage.formularioVisible(),
            "El formulario no debería avanzar"
        );

        System.out.println(
            "PASS: Formato de correo validado"
        );

        System.out.println(
            "CP-008: Todas las validaciones completadas"
        );
    }
}
