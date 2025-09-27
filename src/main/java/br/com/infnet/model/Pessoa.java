package br.com.infnet.model;

import java.util.Objects;

public class Pessoa {
    private final int id;
    private final String nome;
    private final int idade;
    private final String email;
    private final String cpf;

    public Pessoa(int id, String nome, int idade, String email, String cpf) {
        // Validação do Nome
        if (nome == null || nome.trim().isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        }

        // Validação da Idade
        if (idade < 0 || idade > 120) { // Aumentei a idade máxima para 120
            throw new IllegalArgumentException("Idade inválida.");
        }

        // Validação do Email
        if (email == null || email.trim().isBlank() || !email.trim().contains("@")) {
            throw new IllegalArgumentException("Email inválido.");
        }

        // Validação do CPF
        String cpfLimpo = (cpf == null) ? "" : cpf.trim();
        if (!cpfLimpo.matches("\\d{11}")) { // Verifica se o CPF tem exatamente 11 dígitos numéricos
            throw new IllegalArgumentException("CPF inválido. Deve conter 11 dígitos numéricos.");
        }

        this.id = id;
        this.nome = nome.trim();
        this.idade = idade;
        this.email = email.trim();
        this.cpf = cpfLimpo;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }

    public String getEmail() {
        return email;
    }

    public String getCpf() {
        return cpf;
    }

    @Override
    public String toString() {
        return String.format("Pessoa{id=%d, nome='%s', idade=%d, email='%s', cpf='%s'}", id, nome, idade, email, cpf);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pessoa pessoa = (Pessoa) o;
        return id == pessoa.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
