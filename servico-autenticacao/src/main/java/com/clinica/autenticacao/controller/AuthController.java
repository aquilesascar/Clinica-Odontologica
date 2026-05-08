package com.clinica.autenticacao.controller;

import com.clinica.autenticacao.dto.CadastroUsuarioRequest;
import com.clinica.autenticacao.dto.LoginRequest;
import com.clinica.autenticacao.model.Usuario;
import com.clinica.autenticacao.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioRepository usuarioRepository;

    public AuthController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody CadastroUsuarioRequest request) {
        if (usuarioRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("mensagem", "Username ja cadastrado"));
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(request.getUsername());
        usuario.setSenha(request.getSenha());
        usuario.setRole(request.getRole());

        Usuario salvo = usuarioRepository.save(usuario);

        Map<String, Object> response = new HashMap<>();
        response.put("id", salvo.getId());
        response.put("username", salvo.getUsername());
        response.put("role", salvo.getRole());
        response.put("mensagem", "Usuario cadastrado com sucesso");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(request.getUsername());
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("mensagem", "Credenciais invalidas"));
        }

        Usuario usuario = usuarioOpt.get();
        if (!usuario.getSenha().equals(request.getSenha())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("mensagem", "Credenciais invalidas"));
        }

        return ResponseEntity.ok(Map.of(
                "mensagem", "Login realizado com sucesso",
                "username", usuario.getUsername(),
                "role", usuario.getRole()
        ));
    }
}
