package br.com.infnet.view;

import br.com.infnet.model.Pessoa;
import br.com.infnet.service.PessoaService;
import java.util.List;
import java.util.Scanner;

/**
 * Interface de linha de comando para interação com o sistema CRUD de Pessoa.
 */
public class PessoaView {
    private final PessoaService service;
    private final Scanner scanner;

    public PessoaView(PessoaService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        while (true) {
            System.out.println("\n--- Menu Pessoa ---");
            System.out.println("1. Criar Pessoa");
            System.out.println("2. Consultar Pessoa");
            System.out.println("3. Atualizar Pessoa");
            System.out.println("4. Remover Pessoa");
            System.out.println("5. Listar Pessoas");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = lerInt();
            try {
                switch (opcao) {
                    case 1 -> criarPessoa();
                    case 2 -> consultarPessoa();
                    case 3 -> atualizarPessoa();
                    case 4 -> removerPessoa();
                    case 5 -> listarPessoas();
                    case 0 -> { System.out.println("Saindo..."); return; }
                    default -> System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    private void criarPessoa() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Idade: ");
        int idade = lerInt();
        Pessoa pessoa = service.criarPessoa(nome, idade);
        System.out.println("Pessoa criada: " + pessoa);
    }

    private void consultarPessoa() {
        System.out.print("ID da pessoa: ");
        int id = lerInt();
        Pessoa pessoa = service.consultarPessoa(id);
        System.out.println("Pessoa encontrada: " + pessoa);
    }

    private void atualizarPessoa() {
        System.out.print("ID da pessoa: ");
        int id = lerInt();
        System.out.print("Novo nome: ");
        String nome = scanner.nextLine();
        System.out.print("Nova idade: ");
        int idade = lerInt();
        Pessoa pessoa = service.atualizarPessoa(id, nome, idade);
        System.out.println("Pessoa atualizada: " + pessoa);
    }

    private void removerPessoa() {
        System.out.print("ID da pessoa: ");
        int id = lerInt();
        service.removerPessoa(id);
        System.out.println("Pessoa removida com sucesso.");
    }

    private void listarPessoas() {
        List<Pessoa> pessoas = service.listarPessoas();
        if (pessoas.isEmpty()) {
            System.out.println("Nenhuma pessoa cadastrada.");
        } else {
            pessoas.forEach(System.out::println);
        }
    }

    private int lerInt() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Valor inválido. Digite um número: ");
            }
        }
    }
}

