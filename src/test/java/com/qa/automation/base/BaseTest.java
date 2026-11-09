
package com.qa.automation.base;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class BaseTest {

    protected WebDriver driver;

    @BeforeEach
    public void setUp() {

        ChromeOptions options = new ChromeOptions();

        // Configuración general del navegador
        options.setExperimentalOption(
            "prefs",
            Map.of(
                "profile.default_content_setting_values.popups", 2,
                "profile.default_content_setting_values.notifications", 2,
                "autofill.profile_enabled", false,
                "autofill.credit_card_enabled", false,
                "credentials_enable_service", false
            )
        );

        options.addArguments(
            "--disable-notifications"
        );

        ChromeDriver chromeDriver = new ChromeDriver(options);

        // Bloqueo global de solicitudes publicitarias
        chromeDriver.executeCdpCommand(
            "Network.enable",
            Map.of()
        );

        chromeDriver.executeCdpCommand(
            "Network.setBlockedURLs",
            Map.of(
                "urls",
                List.of(
                    "*://*.doubleclick.net/*",
                    "*://*.googlesyndication.com/*",
                    "*://*.googleadservices.com/*",
                    "*://pagead2.googlesyndication.com/*"
                )
            )
        );

        driver = chromeDriver;

        driver.manage().window().maximize();
    }

    @AfterEach
    public void tearDown() {

        if (driver != null) {
            driver.quit();
            System.out.println("Chrome cerrado correctamente");
        }
    }
}
