package br.com.infnet.controller;

import br.com.infnet.model.Pessoa;
import br.com.infnet.service.PessoaService;
import io.javalin.http.Context;

public class PessoaController {

    private final PessoaService pessoaService = new PessoaService();

    public void getAll(Context ctx) {
        ctx.json(pessoaService.listarPessoas());
    }

    public void create(Context ctx) {
        Pessoa pessoa = ctx.bodyAsClass(Pessoa.class); // Pega o JSON do corpo da requisição e converte para um objeto Pessoa
        Pessoa novaPessoa = pessoaService.criarPessoa(pessoa.getNome(), pessoa.getIdade());
        ctx.status(201).json(novaPessoa);
    }
}
