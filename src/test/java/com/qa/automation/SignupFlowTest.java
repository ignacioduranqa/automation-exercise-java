
package com.qa.automation;

import com.qa.automation.base.BaseTest;
import com.qa.automation.pages.LoginPage;
import com.qa.automation.pages.SignupPage;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SignupFlowTest extends BaseTest {

    @Test
    public void validarRegistroYLoginCompleto() {

        SignupPage signupPage = new SignupPage(driver);
        LoginPage loginPage = new LoginPage(driver);

        WebDriverWait wait = new WebDriverWait(
            driver, Duration.ofSeconds(15)
        );

        String nombre = "QA Automation";

        String correo = "qa" +
            UUID.randomUUID().toString().replace("-", "") +
            "@example.com";

        String password = "QaTest12345!";

        // PASO 1: Abrir página de registro
        signupPage.abrirPagina();

        // PASO 2: Completar registro inicial
        signupPage.iniciarRegistro(nombre, correo);

        System.out.println("PASS: Formulario completo abierto");

        // PASO 3: Completar datos personales
        signupPage.completarFormulario(password);

        // PASO 4: Crear cuenta
        signupPage.crearCuenta();

        assertTrue(
            signupPage.cuentaCreadaCorrectamente(),
            "La cuenta debe crearse correctamente"
        );

        System.out.println("PASS: Cuenta creada correctamente");

        // PASO 5: Continuar
        signupPage.continuar();

        // PASO 6: Verificar sesión iniciada
        By usuarioConectado = By.xpath(
            "//a[contains(.,'Logged in as')]"
        );

        assertTrue(
            wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                    usuarioConectado
                )
            ).isDisplayed(),
            "El usuario debe aparecer conectado"
        );

        System.out.println("PASS: Usuario conectado");

        // PASO 7: Cerrar sesión
        By logout = By.cssSelector("a[href='/logout']");

        wait.until(
            ExpectedConditions.elementToBeClickable(logout)
        ).click();

        // PASO 8: Verificar formulario de login
        assertTrue(
            wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("[data-qa='login-email']")
                )
            ).isDisplayed(),
            "Debe aparecer el formulario de login"
        );

        System.out.println("PASS: Sesión cerrada");

        // PASO 9: Iniciar sesión nuevamente
        loginPage.ingresarCorreo(correo);
        loginPage.ingresarPassword(password);
        loginPage.presionarLogin();

        // PASO 10: Validar inicio de sesión
        assertTrue(
            wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                    usuarioConectado
                )
            ).isDisplayed(),
            "El usuario debe iniciar sesión correctamente"
        );

        System.out.println(
            "CP-011 PASS: Registro, logout y login completados"
        );
    }
}
