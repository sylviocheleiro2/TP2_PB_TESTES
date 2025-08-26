package br.com.infnet.service;

import br.com.infnet.model.Pessoa;
import java.util.*;

/**
 * Serviço responsável pelas operações CRUD de Pessoa.
 */
public class PessoaService {
    private final Map<Integer, Pessoa> pessoas = new HashMap<>();
    private int proximoId = 1;

    // Cria uma nova pessoa
    public Pessoa criarPessoa(String nome, int idade) {
        Pessoa pessoa = new Pessoa(proximoId++, nome, idade);
        pessoas.put(pessoa.getId(), pessoa);
        return pessoa;
    }

    // Consulta pessoa por ID
    public Pessoa consultarPessoa(int id) {
        Pessoa pessoa = pessoas.get(id);
        if (pessoa == null) {
            throw new NoSuchElementException("Pessoa não encontrada.");
        }
        return pessoa;
    }

    // Atualiza dados de uma pessoa
    public Pessoa atualizarPessoa(int id, String nome, int idade) {
        if (!pessoas.containsKey(id)) {
            throw new NoSuchElementException("Pessoa não encontrada para atualização.");
        }
        Pessoa pessoaAtualizada = new Pessoa(id, nome, idade);
        pessoas.put(id, pessoaAtualizada);
        return pessoaAtualizada;
    }

    // Remove pessoa por ID
    public void removerPessoa(int id) {
        if (pessoas.remove(id) == null) {
            throw new NoSuchElementException("Pessoa não encontrada para remoção.");
        }
    }

    // Lista todas as pessoas
    public List<Pessoa> listarPessoas() {
        return new ArrayList<>(pessoas.values());
    }
}

