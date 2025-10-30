package br.com.infnet.controller;

import br.com.infnet.model.Pessoa;
import br.com.infnet.service.PessoaService;
import io.javalin.http.Context;

public class PessoaController {

    private final PessoaService pessoaService = new PessoaService();

    public record PessoaRequest(String nome, int idade, String email, String cpf) {}

    public void getAll(Context ctx) {
        ctx.json(pessoaService.listarPessoas());
    }

    public void getOne(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Pessoa pessoa = pessoaService.consultarPessoa(id);
        ctx.json(pessoa);
    }

    public void create(Context ctx) {
        PessoaRequest request = ctx.bodyAsClass(PessoaRequest.class);
        Pessoa novaPessoa = pessoaService.criarPessoa(
            request.nome(),
            request.idade(),
            request.email(),
            request.cpf()
        );
        ctx.status(201).json(novaPessoa);
    }

    public void update(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        PessoaRequest request = ctx.bodyAsClass(PessoaRequest.class);
        Pessoa pessoaAtualizada = pessoaService.atualizarPessoa(
            id,
            request.nome(),
            request.idade(),
            request.email(),
            request.cpf()
        );
        ctx.json(pessoaAtualizada);
    }

    public void delete(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        pessoaService.removerPessoa(id);
        ctx.status(204); // Sucesso, sem conteúdo para retornar
    }
}
