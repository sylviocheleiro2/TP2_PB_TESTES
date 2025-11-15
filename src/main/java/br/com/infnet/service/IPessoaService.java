package br.com.infnet.service;

import br.com.infnet.model.Pessoa;

import java.util.List;

public interface IPessoaService
{
    Pessoa criarPessoa(String nome, int idade, String email, String cpf);
    Pessoa consultarPessoa(int id);
    Pessoa atualizarPessoa(int id, String nome, int idade, String email, String cpf);
    void removerPessoa(int id);
    List<Pessoa> listarPessoas();
}
