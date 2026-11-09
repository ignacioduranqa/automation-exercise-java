
package com.qa.automation.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignupPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // =====================================
    // FORMULARIO INICIAL - CP-008
    // =====================================

    private final By campoNombre =
        By.cssSelector("[data-qa='signup-name']");

    private final By campoCorreo =
        By.cssSelector("[data-qa='signup-email']");

    private final By botonSignup =
        By.cssSelector("[data-qa='signup-button']");

    // =====================================
    // REGISTRO COMPLETO - CP-011
    // =====================================

    private final By campoPassword = By.id("password");

    private final By campoDias = By.id("days");
    private final By campoMeses = By.id("months");
    private final By campoAnos = By.id("years");

    private final By campoPrimerNombre = By.id("first_name");
    private final By campoApellido = By.id("last_name");
    private final By campoEmpresa = By.id("company");
    private final By campoDireccion = By.id("address1");
    private final By campoDireccion2 = By.id("address2");

    private final By campoPais = By.id("country");
    private final By campoEstado = By.id("state");
    private final By campoCiudad = By.id("city");
    private final By campoCodigoPostal = By.id("zipcode");
    private final By campoTelefono = By.id("mobile_number");

    private final By botonCrearCuenta =
        By.cssSelector("[data-qa='create-account']");

    private final By mensajeCuentaCreada =
        By.cssSelector("[data-qa='account-created']");

    private final By botonContinuar =
        By.cssSelector("[data-qa='continue-button']");

    // Constructor
    public SignupPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
            driver,
            Duration.ofSeconds(15)
        );
    }

    // =====================================
    // MÉTODOS ORIGINALES - CP-008
    // =====================================

    public void abrirPagina() {

        driver.get("https://automationexercise.com/login");

        wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                campoNombre
            )
        );
    }

    public void ingresarNombre(String nombre) {

        WebElement campo = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                campoNombre
            )
        );

        campo.clear();
        campo.sendKeys(nombre);
    }

    public void ingresarCorreo(String correo) {

        WebElement campo = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                campoCorreo
            )
        );

        campo.clear();
        campo.sendKeys(correo);
    }

    public void presionarSignup() {

        wait.until(
            ExpectedConditions.elementToBeClickable(
                botonSignup
            )
        ).click();
    }

    public boolean nombreValido() {

        WebElement campo = driver.findElement(campoNombre);

        return Boolean.TRUE.equals(
            ((JavascriptExecutor) driver).executeScript(
                "return arguments[0].checkValidity();",
                campo
            )
        );
    }

    public boolean correoValido() {

        WebElement campo = driver.findElement(campoCorreo);

        return Boolean.TRUE.equals(
            ((JavascriptExecutor) driver).executeScript(
                "return arguments[0].checkValidity();",
                campo
            )
        );
    }

    public boolean formularioVisible() {

        return wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                botonSignup
            )
        ).isDisplayed();
    }

    // =====================================
    // MÉTODOS NUEVOS - CP-011
    // =====================================

    // Abrir el formulario completo
    public void iniciarRegistro(String nombre, String correo) {

        ingresarNombre(nombre);
        ingresarCorreo(correo);
        presionarSignup();

        wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                campoPassword
            )
        );
    }

    // Completar todos los datos del usuario
    public void completarFormulario(String password) {

        wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                campoPassword
            )
        ).sendKeys(password);

        new Select(driver.findElement(campoDias))
            .selectByVisibleText("15");

        new Select(driver.findElement(campoMeses))
            .selectByVisibleText("May");

        new Select(driver.findElement(campoAnos))
            .selectByVisibleText("1998");

        driver.findElement(campoPrimerNombre)
            .sendKeys("QA");

        driver.findElement(campoApellido)
            .sendKeys("Automation");

        driver.findElement(campoEmpresa)
            .sendKeys("QA Testing");

        driver.findElement(campoDireccion)
            .sendKeys("Test Street 123");

        driver.findElement(campoDireccion2)
            .sendKeys("Apartment 10");

        new Select(driver.findElement(campoPais))
            .selectByVisibleText("Canada");

        driver.findElement(campoEstado)
            .sendKeys("Ontario");

        driver.findElement(campoCiudad)
            .sendKeys("Toronto");

        driver.findElement(campoCodigoPostal)
            .sendKeys("10000");

        driver.findElement(campoTelefono)
            .sendKeys("1234567890");
    }

   
public void crearCuenta() {

    WebElement boton = wait.until(
        ExpectedConditions.presenceOfElementLocated(
            botonCrearCuenta
        )
    );

    // Desplazar el botón al centro de la pantalla
    ((JavascriptExecutor) driver).executeScript(
        "arguments[0].scrollIntoView({block: 'center'});",
        boton
    );

    // Esperar a que sea posible hacer clic
    wait.until(
        ExpectedConditions.elementToBeClickable(
            botonCrearCuenta
        )
    ).click();
}


    // Validar creación de cuenta
    public boolean cuentaCreadaCorrectamente() {

        return wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                mensajeCuentaCreada
            )
        ).isDisplayed();
    }

    // Continuar después de crear la cuenta
    public void continuar() {

        wait.until(
            ExpectedConditions.elementToBeClickable(
                botonContinuar
            )
        ).click();
    }
}
