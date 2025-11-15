package br.com.infnet.service;

import br.com.infnet.model.Pessoa;
import br.com.infnet.repository.PessoaRepository;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Serviço responsável pelas operações de negócio de Pessoa.
 */
public class PessoaService implements IPessoaService {

    private final PessoaRepository pessoaRepository = PessoaRepository.getInstance();

    @Override
    public Pessoa criarPessoa(String nome, int idade, String email, String cpf) {
        // A validação agora é feita no construtor de Pessoa
        Pessoa novaPessoa = new Pessoa(0, nome, idade, email, cpf); // ID é gerenciado pelo repositório
        return pessoaRepository.save(novaPessoa);
    }

    @Override
    public Pessoa consultarPessoa(int id) {
        return pessoaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Pessoa não encontrada."));
    }

    @Override
    public Pessoa atualizarPessoa(int id, String nome, int idade, String email, String cpf) {
        // A validação é feita no construtor de Pessoa
        Pessoa pessoaAtualizada = new Pessoa(id, nome, idade, email, cpf);
        Pessoa pessoa = pessoaRepository.update(pessoaAtualizada);
        if (pessoa == null) {
            throw new NoSuchElementException("Pessoa não encontrada para atualização.");
        }
        return pessoa;
    }

    @Override
    public void removerPessoa(int id) {
        boolean removed = pessoaRepository.deleteById(id);
        if (!removed) {
            throw new NoSuchElementException("Pessoa não encontrada para remoção.");
        }
    }

    @Override
    public List<Pessoa> listarPessoas() {
        return pessoaRepository.findAll();
    }
}
