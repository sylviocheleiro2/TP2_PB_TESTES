package br.com.infnet.model;


public class Pessoa {
    private final int id;
    private final String nome;
    private final int idade;

    public Pessoa(int id, String nome, int idade) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        }
        if (idade < 0 || idade > 150) {
            throw new IllegalArgumentException("Idade inválida.");
        }
        this.id = id;
        this.nome = nome;
        this.idade = idade;
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

    @Override
    public String toString() {
        return String.format("Pessoa{id=%d, nome='%s', idade=%d}", id, nome, idade);
    }
}

