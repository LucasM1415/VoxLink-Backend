package org.example.voxlink_backend.Controllers;

import lombok.RequiredArgsConstructor;
import org.example.voxlink_backend.Service.JwtService;
import org.example.voxlink_backend.Service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            // 1. Autenticação (lança exception se falhar)
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.codigoLogin(),
                            request.senha()
                    )
            );

            // 2. Geração do token
            String token = jwtService.gerarToken(request.codigoLogin());

            // 3. Busca informações do usuário
            var usuario = usuarioService.buscarPorCodigo(request.codigoLogin())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado após autenticação"));

            // 4. Logs para debug
            System.out.println("Login bem-sucedido:");
            System.out.println("Código: " + usuario.getCodigoLogin());
            System.out.println("Role: " + usuario.getRole());
            System.out.println("Token: " + token);

            // 5. Retorna resposta estruturada
            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "codigoLogin", usuario.getCodigoLogin(),
                    "role", usuario.getRole()
            ));

        } catch (AuthenticationException e) {
            System.err.println("Falha na autenticação: " + e.getMessage());
            return ResponseEntity.status(401).body(Map.of(
                    "mensagem", "Credenciais inválidas",
                    "erro", e.getMessage()
            ));
        }
    }

    public record LoginRequest(String codigoLogin, String senha) {}
}