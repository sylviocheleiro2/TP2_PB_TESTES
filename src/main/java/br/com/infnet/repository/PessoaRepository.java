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

    public Pessoa save(Pessoa pessoa) {
        Pessoa novaPessoa = new Pessoa(proximoId++, pessoa.getNome(), pessoa.getIdade(), pessoa.getEmail(), pessoa.getCpf());
        pessoas.put(novaPessoa.getId(), novaPessoa);
        return novaPessoa;
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
    
    public Pessoa update(Pessoa pessoa) {
        if (!pessoas.containsKey(pessoa.getId())) {
            return null; // Ou lançar exceção
        }
        pessoas.put(pessoa.getId(), pessoa);
        return pessoa;
    }
}
