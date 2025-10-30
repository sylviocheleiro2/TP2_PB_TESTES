package br.com.infnet.tests;

import br.com.infnet.pages.CadastroPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PessoaCrudE2ETest extends BaseTest {

    @Test
    @DisplayName("Deve cadastrar uma nova pessoa com sucesso")
    public void deveCadastrarNovaPessoaComSucesso() {
        CadastroPage cadastroPage = new CadastroPage(driver);
        String nome = "Fulano de Tal";
        String idade = "30";
        String email = "fulano@teste.com";
        String cpf = "12345678901";

        cadastroPage.preencherFormularioDeCadastro(nome, idade, email, cpf);
        cadastroPage.clicarEmAdicionar();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(cadastroPage.getUltimaPessoaDaLista()));

        String textoDoUltimoItem = cadastroPage.getTextoUltimaPessoaDaLista();
        assertTrue(textoDoUltimoItem.contains(nome));
        assertTrue(textoDoUltimoItem.contains(email));
        assertTrue(textoDoUltimoItem.contains(cpf));
    }

    @Test
    @DisplayName("Deve editar uma pessoa com sucesso")
    public void deveEditarPessoaComSucesso() {
        CadastroPage cadastroPage = new CadastroPage(driver);
        cadastroPage.preencherFormularioDeCadastro("Pessoa Original", "25", "original@email.com", "11122233344");
        cadastroPage.clicarEmAdicionar();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(cadastroPage.getUltimaPessoaDaLista()));

        cadastroPage.clicarEmEditarNaUltimaPessoa();
        wait.until(ExpectedConditions.visibilityOf(cadastroPage.getEditModal()));

        String nomeEditado = "Pessoa Editada";
        String emailEditado = "editado@email.com";
        cadastroPage.preencherFormularioDeEdicao(nomeEditado, "35", emailEditado, "55566677788");
        cadastroPage.clicarEmSalvar();

        wait.until(ExpectedConditions.invisibilityOf(cadastroPage.getEditModal()));
        String textoDoItemAtualizado = cadastroPage.getTextoUltimaPessoaDaLista();
        assertTrue(textoDoItemAtualizado.contains(nomeEditado));
        assertTrue(textoDoItemAtualizado.contains(emailEditado));
    }

    @Test
    @DisplayName("Deve remover uma pessoa com sucesso")
    public void deveRemoverPessoaComSucesso() {
        CadastroPage cadastroPage = new CadastroPage(driver);
        cadastroPage.preencherFormularioDeCadastro("Pessoa a Remover", "40", "remover@email.com", "99988877766");
        cadastroPage.clicarEmAdicionar();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(cadastroPage.getUltimaPessoaDaLista()));
        String textoPessoaRemovida = cadastroPage.getTextoUltimaPessoaDaLista();

        cadastroPage.clicarEmRemoverNaUltimaPessoa();
        driver.switchTo().alert().accept();

        wait.until(ExpectedConditions.stalenessOf(cadastroPage.getUltimaPessoaDaLista()));
        assertFalse(driver.getPageSource().contains(textoPessoaRemovida), "A pessoa removida ainda foi encontrada na página.");
    }

    @ParameterizedTest
    @DisplayName("Deve exibir erro ao tentar cadastrar pessoa com dados inválidos")
    @CsvSource({
        "'', '25', 'teste@email.com', '12345678901', 'Nome não pode ser vazio.'",
        "'Nome Valido', '30', 'email-invalido', '12345678901', 'Email inválido.'",
        "'Nome Valido', '30', 'teste@email.com', '123', 'CPF inválido.'"
    })
    public void deveExibirErroAoTentarCadastrarPessoaComDadosInvalidos(String nome,
                                                                       String idade,
                                                                       String email,
                                                                       String cpf,
                                                                       String mensagemEsperada) {
        CadastroPage cadastroPage = new CadastroPage(driver);

        cadastroPage.preencherFormularioDeCadastro(nome, idade, email, cpf);
        cadastroPage.clicarEmAdicionar();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String alertText = alert.getText();
        alert.accept();

        assertTrue(alertText.contains(mensagemEsperada),
            String.format("Mensagem de erro esperada '%s' não encontrada em '%s'",
                mensagemEsperada, alertText));
    }
}
