package br.com.aweb.sistema_vendas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    // O POST /login é tratado pelo Spring Security
    @GetMapping("/login")
    public String exibirLogin() {
        return "login";
    }

    // Página de confirmação; o POST /logout (com CSRF) fica dentro dela
    @GetMapping("/logout")
    public String exibirLogout() {
        return "logout";
    }
}
