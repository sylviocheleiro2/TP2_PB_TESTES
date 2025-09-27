package br.com.infnet.service;

import br.com.infnet.model.Pessoa;
import br.com.infnet.repository.PessoaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class PessoaServiceTest {

    private PessoaService pessoaService;

    @BeforeEach
    void setUp() throws Exception {
        pessoaService = new PessoaService();

        // Reseta o repositório antes de cada teste para garantir isolamento
        Field instanceField = PessoaRepository.class.getDeclaredField("INSTANCE");
        instanceField.setAccessible(true);
        PessoaRepository repository = (PessoaRepository) instanceField.get(null);

        Field mapField = PessoaRepository.class.getDeclaredField("pessoas");
        mapField.setAccessible(true);
        Map<Integer, Pessoa> pessoasMap = (Map<Integer, Pessoa>) mapField.get(repository);
        pessoasMap.clear();

        Field idField = PessoaRepository.class.getDeclaredField("proximoId");
        idField.setAccessible(true);
        idField.set(repository, 1);
    }

    @Test
    @DisplayName("Deve criar uma pessoa através do serviço")
    void deveCriarPessoa() {
        Pessoa pessoa = pessoaService.criarPessoa("Pessoa de Serviço", 35, "service@email.com", "55566677788");
        assertNotNull(pessoa);
        assertEquals(1, pessoa.getId());
    }

    @Test
    @DisplayName("Deve consultar uma pessoa existente")
    void deveConsultarPessoaExistente() {
        pessoaService.criarPessoa("Pessoa Consulta", 40, "consulta@email.com", "66677788899");
        Pessoa pessoaEncontrada = pessoaService.consultarPessoa(1);
        assertNotNull(pessoaEncontrada);
        assertEquals("Pessoa Consulta", pessoaEncontrada.getNome());
    }

    @Test
    @DisplayName("Deve lançar exceção ao consultar pessoa inexistente")
    void deveLancarExcecaoAoConsultarPessoaInexistente() {
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            pessoaService.consultarPessoa(999);
        });
        assertEquals("Pessoa não encontrada.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve atualizar uma pessoa através do serviço")
    void deveAtualizarPessoa() {
        pessoaService.criarPessoa("Original", 50, "original@service.com", "77788899900");
        Pessoa pessoaAtualizada = pessoaService.atualizarPessoa(1, "Atualizada", 51, "atualizada@service.com", "77788899901");

        assertEquals("Atualizada", pessoaAtualizada.getNome());
        assertEquals(51, pessoaAtualizada.getIdade());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar atualizar pessoa inexistente")
    void deveLancarExcecaoAoAtualizarPessoaInexistente() {
        assertThrows(NoSuchElementException.class, () -> {
            pessoaService.atualizarPessoa(999, "Inexistente", 20, "inexistente@email.com", "00000000000");
        });
    }

    @Test
    @DisplayName("Deve remover uma pessoa através do serviço")
    void deveRemoverPessoa() {
        pessoaService.criarPessoa("A ser removida", 60, "remover@service.com", "88899900011");
        assertDoesNotThrow(() -> pessoaService.removerPessoa(1));
        assertThrows(NoSuchElementException.class, () -> pessoaService.consultarPessoa(1));
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar remover pessoa inexistente")
    void deveLancarExcecaoAoRemoverPessoaInexistente() {
        assertThrows(NoSuchElementException.class, () -> {
            pessoaService.removerPessoa(999);
        });
    }
}
