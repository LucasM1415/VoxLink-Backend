package org.example.voxlink_backend.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void enviarCodigoLogin(String destinoEmail, String codigoLogin) {
        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo(destinoEmail);
        mensagem.setSubject("Seu Código de Login na VoxLink");
        mensagem.setText("Olá!\n\nAqui está o seu código de login: " + codigoLogin + "\n\nObrigado por se cadastrar!");

        mailSender.send(mensagem);
    }
}
