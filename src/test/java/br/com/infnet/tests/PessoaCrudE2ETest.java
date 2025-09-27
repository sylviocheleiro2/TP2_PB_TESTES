package br.com.infnet.tests;

import br.com.infnet.pages.PessoaPage;
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
        PessoaPage pessoaPage = new PessoaPage(driver);
        String nome = "Fulano de Tal";
        String idade = "30";
        String email = "fulano@teste.com";
        String cpf = "12345678901";

        pessoaPage.preencherFormularioDeCadastro(nome, idade, email, cpf);
        pessoaPage.clicarEmAdicionar();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(pessoaPage.getUltimaPessoaDaLista()));

        String textoDoUltimoItem = pessoaPage.getTextoUltimaPessoaDaLista();
        assertTrue(textoDoUltimoItem.contains(nome));
        assertTrue(textoDoUltimoItem.contains(email));
        assertTrue(textoDoUltimoItem.contains(cpf));
    }

    @Test
    @DisplayName("Deve editar uma pessoa com sucesso")
    public void deveEditarPessoaComSucesso() {
        PessoaPage pessoaPage = new PessoaPage(driver);
        pessoaPage.preencherFormularioDeCadastro("Pessoa Original", "25", "original@email.com", "11122233344");
        pessoaPage.clicarEmAdicionar();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(pessoaPage.getUltimaPessoaDaLista()));

        pessoaPage.clicarEmEditarNaUltimaPessoa();
        wait.until(ExpectedConditions.visibilityOf(pessoaPage.getEditModal()));

        String nomeEditado = "Pessoa Editada";
        String emailEditado = "editado@email.com";
        pessoaPage.preencherFormularioDeEdicao(nomeEditado, "35", emailEditado, "55566677788");
        pessoaPage.clicarEmSalvar();

        wait.until(ExpectedConditions.invisibilityOf(pessoaPage.getEditModal()));
        String textoDoItemAtualizado = pessoaPage.getTextoUltimaPessoaDaLista();
        assertTrue(textoDoItemAtualizado.contains(nomeEditado));
        assertTrue(textoDoItemAtualizado.contains(emailEditado));
    }

    @Test
    @DisplayName("Deve remover uma pessoa com sucesso")
    public void deveRemoverPessoaComSucesso() {
        PessoaPage pessoaPage = new PessoaPage(driver);
        pessoaPage.preencherFormularioDeCadastro("Pessoa a Remover", "40", "remover@email.com", "99988877766");
        pessoaPage.clicarEmAdicionar();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(pessoaPage.getUltimaPessoaDaLista()));
        String textoPessoaRemovida = pessoaPage.getTextoUltimaPessoaDaLista();

        pessoaPage.clicarEmRemoverNaUltimaPessoa();
        driver.switchTo().alert().accept();

        wait.until(ExpectedConditions.stalenessOf(pessoaPage.getUltimaPessoaDaLista()));
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
        PessoaPage pessoaPage = new PessoaPage(driver);

        pessoaPage.preencherFormularioDeCadastro(nome, idade, email, cpf);
        pessoaPage.clicarEmAdicionar();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String alertText = alert.getText();
        alert.accept();

        assertTrue(alertText.contains(mensagemEsperada), "A mensagem de erro do alerta não era a esperada.");
    }
}
