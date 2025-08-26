package br.com.infnet;

import br.com.infnet.model.Pessoa;
import br.com.infnet.service.PessoaService;
import net.jqwik.api.*;
import net.jqwik.api.constraints.*;
import org.junit.jupiter.api.Assertions;
import java.util.NoSuchElementException;



class PessoaServicePropertyTest {
    @Property
    void deveCriarPessoaValida(@ForAll @AlphaChars @StringLength(min = 1, max = 30) String nome,
                               @ForAll @IntRange(min = 0, max = 150) int idade) {
        PessoaService service = new PessoaService();
        Pessoa pessoa = service.criarPessoa(nome, idade);
        Assertions.assertEquals(nome, pessoa.getNome());
        Assertions.assertEquals(idade, pessoa.getIdade());
    }

    @Property
    void naoDeveCriarPessoaComNomeInvalido(@ForAll("nomesInvalidos") String nome) {
        PessoaService service = new PessoaService();
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.criarPessoa(nome, 20));
    }

    @Provide
    Arbitrary<String> nomesInvalidos() {
        return Arbitraries.strings().ofMinLength(0).ofMaxLength(0).injectNull(0.5);
    }

    @Property
    void naoDeveCriarPessoaComIdadeInvalida(@ForAll("idadesInvalidas") int idade) {
        PessoaService service = new PessoaService();
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.criarPessoa("Teste", idade));
    }

    @Provide
    Arbitrary<Integer> idadesInvalidas() {
        return Arbitraries.integers().between(-1000, 2000)
            .filter(i -> i < 0 || i > 150);
    }

    @Property
    void deveRemoverPessoaCorretamente(@ForAll @AlphaChars @StringLength(min = 1, max = 30) String nome,
                                       @ForAll @IntRange(min = 0, max = 150) int idade) {
        PessoaService service = new PessoaService();
        Pessoa pessoa = service.criarPessoa(nome, idade);
        service.removerPessoa(pessoa.getId());
        Assertions.assertThrows(NoSuchElementException.class, () -> service.consultarPessoa(pessoa.getId()));
    }

    @Property
    void deveAtualizarPessoaCorretamente(@ForAll @AlphaChars @StringLength(min = 1, max = 30) String nome,
                                         @ForAll @IntRange(min = 0, max = 150) int idade,
                                         @ForAll @AlphaChars @StringLength(min = 1, max = 30) String novoNome,
                                         @ForAll @IntRange(min = 0, max = 150) int novaIdade) {
        PessoaService service = new PessoaService();
        Pessoa pessoa = service.criarPessoa(nome, idade);
        Pessoa atualizada = service.atualizarPessoa(pessoa.getId(), novoNome, novaIdade);
        Assertions.assertEquals(novoNome, atualizada.getNome());
        Assertions.assertEquals(novaIdade, atualizada.getIdade());
    }

    @Property
    void deveListarPessoasCorretamente(@ForAll @AlphaChars @StringLength(min = 1, max = 30) String nome,
                                       @ForAll @IntRange(min = 0, max = 150) int idade) {
        PessoaService service = new PessoaService();
        Pessoa pessoa = service.criarPessoa(nome, idade);
        Assertions.assertTrue(service.listarPessoas().contains(pessoa));
    }
}
