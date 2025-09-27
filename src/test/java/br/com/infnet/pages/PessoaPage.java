package br.com.infnet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PessoaPage {

    private final WebDriver driver;

    // Elementos do Formulário de Cadastro
    private final By nomeInput = By.id("nome");
    private final By idadeInput = By.id("idade");
    private final By emailInput = By.id("email");
    private final By cpfInput = By.id("cpf");
    private final By adicionarButton = By.className("btn-submit");

    // Elementos da Lista
    private final By personList = By.id("person-list");

    public PessoaPage(WebDriver driver) {
        this.driver = driver;
    }

    public void preencherFormularioDeCadastro(String nome, String idade, String email, String cpf) {
        driver.findElement(nomeInput).sendKeys(nome);
        driver.findElement(idadeInput).sendKeys(idade);
        driver.findElement(emailInput).sendKeys(email);
        driver.findElement(cpfInput).sendKeys(cpf);
    }

    public void clicarEmAdicionar() {
        driver.findElement(adicionarButton).click();
    }

    public WebElement getUltimaPessoaDaLista() {
        // Encontra o último elemento <li> dentro da <ul> person-list
        return driver.findElement(personList).findElement(By.xpath("./li[last()]"));
    }

    public String getTextoUltimaPessoaDaLista() {
        return getUltimaPessoaDaLista().getText();
    }
}
