package br.com.infnet;

import br.com.infnet.controller.PessoaController;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

public class App {
    public static void main(String[] args) {
        PessoaController pessoaController = new PessoaController();

        Javalin app = Javalin.create(config -> {
            config.staticFiles.add(staticFiles -> {
                staticFiles.hostedPath = "/";
                staticFiles.directory = "/static";
                staticFiles.location = Location.CLASSPATH;
            });
        }).start(7070);

        // Define as rotas da API
        app.get("/api/pessoas", pessoaController::getAll);
        app.get("/api/pessoas/{id}", pessoaController::getOne);   // Nova rota GET para uma pessoa
        app.post("/api/pessoas", pessoaController::create);
        app.put("/api/pessoas/{id}", pessoaController::update);
        app.delete("/api/pessoas/{id}", pessoaController::delete);

        System.out.println("Servidor rodando em http://localhost:7070");
        System.out.println("Acesse a interface em http://localhost:7070/index.html");
    }
}
