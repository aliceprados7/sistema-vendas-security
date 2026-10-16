package br.com.aweb.sistema_vendas.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.com.aweb.sistema_vendas.model.Usuario;
import br.com.aweb.sistema_vendas.model.UsuarioRole;
import br.com.aweb.sistema_vendas.service.UsuarioService;

/**
 * Cria os dois usuários de teste na primeira execução (local ou Render).
 * As senhas passam pelo UsuarioService, então são gravadas com hash BCrypt.
 */
@Component
public class CarregadorUsuariosIniciais implements CommandLineRunner {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public void run(String... args) {
        criarSeNaoExistir("Administrador", "admin@vendas.com", "Admin@123", UsuarioRole.ADMIN);
        criarSeNaoExistir("Usuário Padrão", "padrao@vendas.com", "Padrao@123", UsuarioRole.PADRAO);
    }

    private void criarSeNaoExistir(String nome, String email, String senha, UsuarioRole role) {
        if (!usuarioService.existePorEmail(email)) {
            usuarioService.salvar(new Usuario(null, nome, email, senha, role));
        }
    }
}
