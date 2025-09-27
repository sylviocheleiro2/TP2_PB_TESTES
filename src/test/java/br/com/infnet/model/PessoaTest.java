package br.com.infnet.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PessoaTest {

    @Test
    @DisplayName("Deve criar uma pessoa com dados válidos")
    void deveCriarPessoaComDadosValidos() {
        Pessoa pessoa = new Pessoa(1, "Nome Válido", 30, "email@valido.com", "12345678901");
        assertNotNull(pessoa);
        assertEquals("Nome Válido", pessoa.getNome());
    }

    @Test
    @DisplayName("Não deve criar pessoa com nome nulo")
    void naoDeveCriarPessoaComNomeNulo() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Pessoa(1, null, 30, "email@valido.com", "12345678901");
        });
        assertEquals("Nome não pode ser vazio.", exception.getMessage());
    }

    @Test
    @DisplayName("Não deve criar pessoa com idade negativa")
    void naoDeveCriarPessoaComIdadeNegativa() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Pessoa(1, "Nome Válido", -1, "email@valido.com", "12345678901");
        });
        assertEquals("Idade inválida.", exception.getMessage());
    }

    @Test
    @DisplayName("Não deve criar pessoa com email inválido")
    void naoDeveCriarPessoaComEmailInvalido() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Pessoa(1, "Nome Válido", 30, "email-invalido", "12345678901");
        });
        assertEquals("Email inválido.", exception.getMessage());
    }

    @Test
    @DisplayName("Não deve criar pessoa com CPF inválido")
    void naoDeveCriarPessoaComCpfInvalido() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Pessoa(1, "Nome Válido", 30, "email@valido.com", "123");
        });
        assertEquals("CPF inválido. Deve conter 11 dígitos numéricos.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve considerar duas pessoas com mesmo ID como iguais")
    void deveConsiderarPessoasComMesmoIdIguais() {
        Pessoa pessoa1 = new Pessoa(1, "Nome Um", 30, "email1@valido.com", "11111111111");
        Pessoa pessoa2 = new Pessoa(1, "Nome Dois", 40, "email2@valido.com", "22222222222");
        assertEquals(pessoa1, pessoa2);
        assertEquals(pessoa1.hashCode(), pessoa2.hashCode());
    }
}
