package com.clinica.autenticacao.controller;

import com.clinica.autenticacao.model.Usuario;
import com.clinica.autenticacao.repository.UsuarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class AuthPageController {

    private final UsuarioRepository usuarioRepository;

    public AuthPageController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/register-page")
    public String registerPage() {
        return "auth-register";
    }

    @PostMapping("/register-form")
    public String registerForm(
            @RequestParam String username,
            @RequestParam String senha,
            @RequestParam String role,
            Model model) {

        if (usuarioRepository.findByUsername(username).isPresent()) {
            model.addAttribute("erro", "Username ja cadastrado.");
            return "auth-register";
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setSenha(senha);
        usuario.setRole(role);
        usuarioRepository.save(usuario);

        model.addAttribute("sucesso", "Usuario cadastrado com sucesso. Faca login.");
        return "auth-login";
    }

    @GetMapping("/login-page")
    public String loginPage() {
        return "auth-login";
    }

    @PostMapping("/login-form")
    public String loginForm(
            @RequestParam String username,
            @RequestParam String senha,
            Model model) {

        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(username);
        if (usuarioOpt.isEmpty() || !usuarioOpt.get().getSenha().equals(senha)) {
            model.addAttribute("erro", "Credenciais invalidas.");
            return "auth-login";
        }

        model.addAttribute("username", usuarioOpt.get().getUsername());
        model.addAttribute("role", usuarioOpt.get().getRole());
        return "auth-home";
    }
}
