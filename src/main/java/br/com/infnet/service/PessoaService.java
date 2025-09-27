package br.com.infnet.service;

import br.com.infnet.model.Pessoa;
import br.com.infnet.repository.PessoaRepository;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Serviço responsável pelas operações de negócio de Pessoa.
 */
public class PessoaService {

    private final PessoaRepository pessoaRepository = PessoaRepository.getInstance();

    public Pessoa criarPessoa(String nome, int idade, String email, String cpf) {
        return pessoaRepository.save(nome, idade, email, cpf);
    }

    public Pessoa consultarPessoa(int id) {
        return pessoaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Pessoa não encontrada."));
    }

    public Pessoa atualizarPessoa(int id, String nome, int idade, String email, String cpf) {
        Pessoa pessoa = pessoaRepository.update(id, nome, idade, email, cpf);
        if (pessoa == null) {
            throw new NoSuchElementException("Pessoa não encontrada para atualização.");
        }
        return pessoa;
    }

    public void removerPessoa(int id) {
        boolean removed = pessoaRepository.deleteById(id);
        if (!removed) {
            throw new NoSuchElementException("Pessoa não encontrada para remoção.");
        }
    }

    public List<Pessoa> listarPessoas() {
        return pessoaRepository.findAll();
    }
}
