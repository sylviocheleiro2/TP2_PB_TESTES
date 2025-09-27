package br.com.infnet.controller;

import br.com.infnet.model.Pessoa;
import br.com.infnet.service.PessoaService;
import io.javalin.http.Context;
import java.util.NoSuchElementException;

public class PessoaController {

    private final PessoaService pessoaService = new PessoaService();

    public record PessoaRequest(String nome, int idade, String email, String cpf) {}

    public void getAll(Context ctx) {
        ctx.json(pessoaService.listarPessoas());
    }

    public void getOne(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Pessoa pessoa = pessoaService.consultarPessoa(id);
            ctx.json(pessoa);
        } catch (NoSuchElementException e) {
            ctx.status(404).result(e.getMessage());
        } catch (NumberFormatException e) {
            ctx.status(400).result("ID inválido.");
        }
    }

    public void create(Context ctx) {
        try {
            PessoaRequest request = ctx.bodyAsClass(PessoaRequest.class);
            Pessoa novaPessoa = pessoaService.criarPessoa(
                request.nome(),
                request.idade(),
                request.email(),
                request.cpf()
            );
            ctx.status(201).json(novaPessoa);
        } catch (IllegalArgumentException e) {
            ctx.status(400).result(e.getMessage());
        } catch (Exception e) {
            ctx.status(500).result("Erro interno no servidor: " + e.getMessage());
        }
    }

    public void update(Context ctx) {
        try {
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
        } catch (NoSuchElementException e) {
            ctx.status(404).result(e.getMessage());
        } catch (IllegalArgumentException e) {
            ctx.status(400).result(e.getMessage());
        } catch (Exception e) {
            ctx.status(500).result("Erro interno no servidor: " + e.getMessage());
        }
    }

    public void delete(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            pessoaService.removerPessoa(id);
            ctx.status(204); // Sucesso, sem conteúdo para retornar
        } catch (NoSuchElementException e) {
            ctx.status(404).result(e.getMessage()); // Pessoa não encontrada
        } catch (NumberFormatException e) {
            ctx.status(400).result("ID inválido.");
        }
    }
}
