package br.com.infnet;

import br.com.infnet.service.PessoaService;
import br.com.infnet.view.PessoaView;


public class App {
    public static void main(String[] args) {
        PessoaService service = new PessoaService();
        PessoaView view = new PessoaView(service);
        view.iniciar();
    }
}
