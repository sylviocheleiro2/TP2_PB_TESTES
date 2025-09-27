package br.com.infnet.tests;

import br.com.infnet.pages.PessoaPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PessoaCrudE2ETest extends BaseTest {

    @Test
    @DisplayName("Deve cadastrar uma nova pessoa com sucesso")
    public void deveCadastrarNovaPessoaComSucesso() {
        // 1. Arrange (Organizar)
        PessoaPage pessoaPage = new PessoaPage(driver);
        String nome = "Fulano de Tal";
        String idade = "30";
        String email = "fulano@teste.com";
        String cpf = "12345678901";

        // 2. Act (Agir)
        pessoaPage.preencherFormularioDeCadastro(nome, idade, email, cpf);
        pessoaPage.clicarEmAdicionar();

        // 3. Assert (Verificar)
        // Espera explícita: Aguarda até que o último item da lista esteja visível
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(pessoaPage.getUltimaPessoaDaLista()));

        String textoDoUltimoItem = pessoaPage.getTextoUltimaPessoaDaLista();

        assertTrue(textoDoUltimoItem.contains(nome), "O nome da pessoa cadastrada não foi encontrado na lista.");
        assertTrue(textoDoUltimoItem.contains("Email: " + email), "O email da pessoa cadastrada não foi encontrado na lista.");
        assertTrue(textoDoUltimoItem.contains("CPF: " + cpf), "O CPF da pessoa cadastrada não foi encontrado na lista.");
    }
}
