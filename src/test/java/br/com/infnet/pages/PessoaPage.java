package br.com.infnet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PessoaPage {

    private final WebDriver driver;
    private static final long SLEEP_TIME = 500; // Tempo de pausa em milissegundos

    // Formulário de Cadastro
    private final By nomeInput = By.id("nome");
    private final By idadeInput = By.id("idade");
    private final By emailInput = By.id("email");
    private final By cpfInput = By.id("cpf");
    private final By adicionarButton = By.xpath("//form[@id='add-form']/button");

    // Lista de Pessoas
    private final By personList = By.id("person-list");

    // Modal de Edição
    private final By editModal = By.id("edit-modal");
    private final By editNomeInput = By.id("edit-nome");
    private final By editIdadeInput = By.id("edit-idade");
    private final By editEmailInput = By.id("edit-email");
    private final By editCpfInput = By.id("edit-cpf");
    private final By salvarButton = By.xpath("//form[@id='edit-form']/button");

    public PessoaPage(WebDriver driver) {
        this.driver = driver;
    }

    // Método auxiliar para a pausa
    private void sleep() {
        try {
            Thread.sleep(SLEEP_TIME);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // --- Ações de Cadastro ---
    public void preencherFormularioDeCadastro(String nome, String idade, String email, String cpf) {
        driver.findElement(nomeInput).sendKeys(nome); sleep();
        driver.findElement(idadeInput).sendKeys(idade); sleep();
        driver.findElement(emailInput).sendKeys(email); sleep();
        driver.findElement(cpfInput).sendKeys(cpf); sleep();
    }

    public void clicarEmAdicionar() {
        driver.findElement(adicionarButton).click();
        sleep();
    }

    // --- Ações na Lista ---
    public WebElement getUltimaPessoaDaLista() {
        return driver.findElement(personList).findElement(By.xpath("./li[last()]"));
    }

    public String getTextoUltimaPessoaDaLista() {
        return getUltimaPessoaDaLista().getText();
    }

    public void clicarEmRemoverNaUltimaPessoa() {
        getUltimaPessoaDaLista().findElement(By.className("btn-delete")).click();
        sleep();
    }

    public void clicarEmEditarNaUltimaPessoa() {
        getUltimaPessoaDaLista().findElement(By.className("btn-edit")).click();
        sleep();
    }

    // --- Ações no Modal de Edição ---
    public void preencherFormularioDeEdicao(String nome, String idade, String email, String cpf) {
        WebElement nomeField = driver.findElement(editNomeInput);
        nomeField.clear(); sleep();
        nomeField.sendKeys(nome); sleep();

        WebElement idadeField = driver.findElement(editIdadeInput);
        idadeField.clear(); sleep();
        idadeField.sendKeys(idade); sleep();

        WebElement emailField = driver.findElement(editEmailInput);
        emailField.clear(); sleep();
        emailField.sendKeys(email); sleep();

        WebElement cpfField = driver.findElement(editCpfInput);
        cpfField.clear(); sleep();
        cpfField.sendKeys(cpf); sleep();
    }

    public void clicarEmSalvar() {
        driver.findElement(salvarButton).click();
        sleep();
    }

    public WebElement getEditModal() {
        return driver.findElement(editModal);
    }
}
