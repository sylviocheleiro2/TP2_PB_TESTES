package br.com.infnet.repository;

import br.com.infnet.model.Pessoa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class PessoaRepositoryTest {

    private PessoaRepository repository;

    @BeforeEach
    @SuppressWarnings("unchecked")
    void setUp() throws Exception {
        // Usa Reflection para resetar o estado do Singleton antes de cada teste
        Field instanceField = PessoaRepository.class.getDeclaredField("INSTANCE");
        instanceField.setAccessible(true);
        repository = (PessoaRepository) instanceField.get(null);

        Field mapField = PessoaRepository.class.getDeclaredField("pessoas");
        mapField.setAccessible(true);
        Map<Integer, Pessoa> pessoasMap = (Map<Integer, Pessoa>) mapField.get(repository);
        pessoasMap.clear();

        Field idField = PessoaRepository.class.getDeclaredField("proximoId");
        idField.setAccessible(true);
        idField.set(repository, 1);
    }

    @Test
    @DisplayName("Deve salvar uma nova pessoa e atribuir um ID")
    void deveSalvarNovaPessoa() {
        Pessoa pessoaSalva = repository.save("Nova Pessoa", 25, "nova@email.com", "11122233344");

        assertNotNull(pessoaSalva);
        assertEquals(1, pessoaSalva.getId());
        assertEquals("Nova Pessoa", pessoaSalva.getNome());
    }

    @Test
    @DisplayName("Deve encontrar uma pessoa pelo ID")
    void deveEncontrarPessoaPorId() {
        repository.save("Pessoa Encontrada", 30, "encontrada@email.com", "22233344455");
        Optional<Pessoa> pessoaOptional = repository.findById(1);

        assertTrue(pessoaOptional.isPresent());
        assertEquals("Pessoa Encontrada", pessoaOptional.get().getNome());
    }

    @Test
    @DisplayName("Deve retornar Optional vazio para ID inexistente")
    void deveRetornarEmptyParaIdInexistente() {
        Optional<Pessoa> pessoaOptional = repository.findById(999);
        assertTrue(pessoaOptional.isEmpty());
    }

    @Test
    @DisplayName("Deve listar todas as pessoas")
    void deveListarTodasAsPessoas() {
        repository.save("Pessoa 1", 20, "p1@email.com", "11111111111");
        repository.save("Pessoa 2", 30, "p2@email.com", "22222222222");

        List<Pessoa> pessoas = repository.findAll();

        assertEquals(2, pessoas.size());
    }

    @Test
    @DisplayName("Deve atualizar uma pessoa existente")
    void deveAtualizarPessoa() {
        repository.save("Pessoa Original", 40, "original@email.com", "33344455566");
        Pessoa pessoaAtualizada = repository.update(1, "Pessoa Atualizada", 41, "atualizado@email.com", "33344455567");

        assertNotNull(pessoaAtualizada);
        assertEquals("Pessoa Atualizada", pessoaAtualizada.getNome());
        assertEquals(41, pessoaAtualizada.getIdade());

        Optional<Pessoa> pessoaDoRepo = repository.findById(1);
        assertTrue(pessoaDoRepo.isPresent());
        assertEquals("Pessoa Atualizada", pessoaDoRepo.get().getNome());
    }

    @Test
    @DisplayName("Deve deletar uma pessoa pelo ID")
    void deveDeletarPessoa() {
        repository.save("Pessoa a Deletar", 50, "deletar@email.com", "44455566677");
        boolean deletado = repository.deleteById(1);

        assertTrue(deletado);
        assertTrue(repository.findById(1).isEmpty());
    }
}
