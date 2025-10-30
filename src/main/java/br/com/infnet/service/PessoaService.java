package br.com.infnet.service;

import br.com.infnet.model.Pessoa;
import br.com.infnet.repository.PessoaRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

/**
 * Serviço responsável pelas operações de negócio de Pessoa.
 */
public class PessoaService {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w._%+-]+@[\\w.-]+\\.[A-Za-z]{2,}$");

    private final PessoaRepository pessoaRepository = PessoaRepository.getInstance();

    public Pessoa criarPessoa(String nome, int idade, String email, String cpf) {
        validarDados(nome, idade, email, cpf);
        return pessoaRepository.save(nome, idade, email, cpf);
    }

    public Pessoa consultarPessoa(int id) {
        return pessoaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Pessoa não encontrada."));
    }

    public Pessoa atualizarPessoa(int id, String nome, int idade, String email, String cpf) {
        validarDados(nome, idade, email, cpf);
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

    private void validarDados(String nome, int idade, String email, String cpf) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        }
        if (idade < 0 || idade > 120) {
            throw new IllegalArgumentException("Idade inválida.");
        }
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Email inválido.");
        }
        if (cpf == null) {
            throw new IllegalArgumentException("CPF inválido.");
        }
        String cpfDigits = cpf.replaceAll("\\D", "");
        if (cpfDigits.length() != 11) {
            throw new IllegalArgumentException("CPF inválido.");
        }
    }
}
