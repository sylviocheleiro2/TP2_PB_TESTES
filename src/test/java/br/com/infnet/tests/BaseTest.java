package br.com.infnet.tests;

import br.com.infnet.App;
import br.com.infnet.pages.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
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
        // Inicia a aplicação Javalin antes de todos os testes
        App.start();
        // Configura o WebDriverManager para o Chrome.
        WebDriverManager.chromedriver().setup();
    }

    @AfterAll
    public static void teardownClass() {
        // Para a aplicação Javalin após todos os testes
        App.stop();
    }

    @BeforeEach
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless"); // Descomente para rodar sem interface gráfica (bom para CI/CD)
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1200");
        options.addArguments("--ignore-certificate-errors");

        // Inicializa o driver do Chrome com as opções configuradas
        driver = new ChromeDriver(options);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        try {
            LoginPage loginPage = new LoginPage(driver);
            loginPage.realizarLogin("admin@example.com", "admin123");

            // Verifica se o login foi bem-sucedido
            if (!loginPage.isLoginSucesso()) {
                String currentUrl = driver.getCurrentUrl();
                throw new RuntimeException("Falha ao realizar login antes do teste. URL atual: " + currentUrl);
            }
        } catch (Exception e) {
            if (driver != null) {
                String pageSource = driver.getPageSource();
                String currentUrl = driver.getCurrentUrl();
                System.err.println("Erro durante o login:");
                System.err.println("URL atual: " + currentUrl);
                System.err.println("HTML da página:");
                System.err.println(pageSource);
                driver.quit();
            }
            throw new RuntimeException("Falha ao realizar login: " + e.getMessage(), e);
        }
    }

    @AfterEach
    public void teardown() {
        // Fecha o navegador e encerra a sessão do WebDriver após cada teste.
        if (driver != null) {
            driver.quit();
        }
    }
}
