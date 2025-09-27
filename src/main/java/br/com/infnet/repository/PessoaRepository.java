package br.com.infnet.repository;

import br.com.infnet.model.Pessoa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PessoaRepository {

    private static final PessoaRepository INSTANCE = new PessoaRepository();
    private final Map<Integer, Pessoa> pessoas = new HashMap<>();
    private int proximoId = 1;

    private PessoaRepository() {
        // Construtor privado para o singleton
    }

    public static PessoaRepository getInstance() {
        return INSTANCE;
    }

    public Pessoa save(String nome, int idade, String email, String cpf) {
        Pessoa pessoa = new Pessoa(proximoId++, nome, idade, email, cpf);
        pessoas.put(pessoa.getId(), pessoa);
        return pessoa;
    }

    public Optional<Pessoa> findById(int id) {
        return Optional.ofNullable(pessoas.get(id));
    }

    public List<Pessoa> findAll() {
        return new ArrayList<>(pessoas.values());
    }

    public boolean deleteById(int id) {
        return pessoas.remove(id) != null;
    }
    
    public Pessoa update(int id, String nome, int idade, String email, String cpf) {
        if (!pessoas.containsKey(id)) {
            return null; // Ou lançar exceção
        }
        Pessoa pessoaAtualizada = new Pessoa(id, nome, idade, email, cpf);
        pessoas.put(id, pessoaAtualizada);
        return pessoaAtualizada;
    }
}
