
package com.qa.automation;

import com.qa.automation.base.BaseTest;
import com.qa.automation.pages.ContactPage;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ContactValidationTest extends BaseTest {

    @Test
    public void validarCorreoFormularioContacto() {

        ContactPage contactPage = new ContactPage(driver);

        System.out.println(
            "CP-009: Validando formulario de contacto"
        );

        // Abrir formulario
        contactPage.abrirPagina();

        // ESCENARIO 1: Correo vacío
        contactPage.ingresarNombre("Usuario QA");
        contactPage.ingresarCorreo("");

        contactPage.presionarEnviar();

        assertFalse(
            contactPage.correoValido(),
            "El correo vacío no debería ser válido"
        );

        assertTrue(
            contactPage.formularioVisible(),
            "El formulario no debería avanzar"
        );

        System.out.println(
            "PASS: Correo obligatorio validado"
        );

        // ESCENARIO 2: Correo inválido
        contactPage.ingresarCorreo("correo-invalido");

        contactPage.presionarEnviar();

        assertFalse(
            contactPage.correoValido(),
            "El correo con formato incorrecto fue aceptado"
        );

        assertTrue(
            contactPage.formularioVisible(),
            "El formulario no debería avanzar"
        );

        System.out.println(
            "PASS: Formato de correo validado"
        );

        System.out.println(
            "CP-009: Validaciones completadas"
        );
    }
}
