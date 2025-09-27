package br.com.infnet.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public abstract class BaseTest {

    protected WebDriver driver;

    @BeforeAll
    public static void setupClass() {
        // Configura o WebDriverManager para o Chrome. Isso baixará e configurará o chromedriver automaticamente.
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setup() {
        // Opções para rodar o Chrome em modo "headless" (sem interface gráfica), ideal para automação.
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1200");
        options.addArguments("--ignore-certificate-errors");

        // Inicializa o driver do Chrome com as opções configuradas
        driver = new ChromeDriver(options);

        // Define um tempo de espera implícito. O Selenium esperará até 10 segundos por um elemento antes de falhar.
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Navega para a URL da nossa aplicação
        driver.get("http://localhost:7070");
    }

    @AfterEach
    public void teardown() {
        // Fecha o navegador e encerra a sessão do WebDriver após cada teste.
        if (driver != null) {
            driver.quit();
        }
    }
}
