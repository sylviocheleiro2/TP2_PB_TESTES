package br.com.infnet;

import br.com.infnet.controller.PessoaController;
import br.com.infnet.model.ErrorResponse;
import io.javalin.Javalin;
import io.javalin.http.HttpStatus;
import io.javalin.http.staticfiles.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    public static void main(String[] args) {
        Logger log = LoggerFactory.getLogger(App.class);
        PessoaController pessoaController = new PessoaController();

        Javalin app = Javalin.create(config ->
        {
            config.staticFiles.add(staticFiles ->
            {
                staticFiles.hostedPath = "/";
                staticFiles.directory = "/static";
                staticFiles.location = Location.CLASSPATH;
            });
        });

        // Handlers globais de erro (fail early/gracefully)
        app.exception(IllegalArgumentException.class, (e, ctx) -> {
            log.warn("[VALIDATION] {} - {}", ctx.path(), e.getMessage());
            ctx.status(HttpStatus.BAD_REQUEST)
               .json(ErrorResponse.of(ctx.path(), "BAD_REQUEST", e.getMessage()));
        });
        app.exception(NumberFormatException.class, (e, ctx) -> {
            log.warn("[BAD_REQUEST] {} - {}", ctx.path(), e.getMessage());
            ctx.status(HttpStatus.BAD_REQUEST)
               .json(ErrorResponse.of(ctx.path(), "BAD_REQUEST", "Requisição inválida."));
        });
        app.exception(java.util.NoSuchElementException.class, (e, ctx) -> {
            log.warn("[NOT_FOUND] {} - {}", ctx.path(), e.getMessage());
            ctx.status(HttpStatus.NOT_FOUND)
               .json(ErrorResponse.of(ctx.path(), "NOT_FOUND", e.getMessage()));
        });
        app.exception(Exception.class, (e, ctx) -> {
            log.error("[INTERNAL_ERROR] {} - {}", ctx.path(), e.toString());
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
               .json(ErrorResponse.of(ctx.path(), "INTERNAL_ERROR", "Erro interno no servidor."));
        });

        app.start(7070);

        // Define as rotas da API
        app.get("/api/pessoas", pessoaController::getAll);
        app.get("/api/pessoas/{id}", pessoaController::getOne);   // Nova rota GET para uma pessoa
        app.post("/api/pessoas", pessoaController::create);
        app.put("/api/pessoas/{id}", pessoaController::update);
        app.delete("/api/pessoas/{id}", pessoaController::delete);

        // Endpoint de login simples (validação de email no backend)
        app.post("/api/login", ctx -> {
            java.util.Map body = ctx.bodyAsClass(java.util.Map.class);
            Object emailObj = body.get("email");
            Object passwordObj = body.get("password");
            String email = emailObj != null ? emailObj.toString() : null;
            String password = passwordObj != null ? passwordObj.toString() : null;

            if (email == null || email.isBlank() || password == null || password.isBlank()) {
                ctx.status(HttpStatus.BAD_REQUEST)
                   .json(ErrorResponse.of(ctx.path(), "BAD_REQUEST", "Email e senha são obrigatórios."));
                return;
            }

            java.util.regex.Pattern emailPattern = java.util.regex.Pattern.compile("^[\\w._%+-]+@[\\w.-]+\\.[A-Za-z]{2,}$");
            if (!emailPattern.matcher(email).matches()) {
                ctx.status(HttpStatus.BAD_REQUEST)
                   .json(ErrorResponse.of(ctx.path(), "BAD_REQUEST", "Email inválido."));
                return;
            }

            log.info("Login bem-sucedido para {}", email);
            ctx.status(HttpStatus.OK).json(java.util.Map.of("message", "Login efetuado com sucesso"));
        });

        log.info("Servidor rodando em http://localhost:7070");
        log.info("Acesse o login em http://localhost:7070/index.html ou a tela de cadastro em http://localhost:7070/cadastro.html");
    }
}
