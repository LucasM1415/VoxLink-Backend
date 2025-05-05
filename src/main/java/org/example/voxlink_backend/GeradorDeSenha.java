package org.example.voxlink_backend;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeradorDeSenha {

    public static void main(String[] args) {
        // Cria o encoder para criptografar a senha
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // Senha simples para ser criptografada
        String senha = "12345678";

        // Criptografa a senha
        String senhaCriptografada = passwordEncoder.encode(senha);

        // Exibe a senha criptografada no console
        System.out.println("Senha Criptografada: " + senhaCriptografada);
    }
}
