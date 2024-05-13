package com.residencia18.api.service;

import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@Slf4j
public class EmailService {

    // Injeta o JavaMailSender para enviar e-mails
    @Autowired
    private final JavaMailSender javaMailSender;

    // Construtor para inicializar o JavaMailSender
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    // Método para enviar e-mail
    public void sendEmail(String to, String subject, String body) throws MessagingException {
        // Registra no log que está enviando um e-mail para o destinatário
        log.info("Sending email to: {}", to);

        // Cria uma mensagem MIME para e-mail
        var message = javaMailSender.createMimeMessage();
        var helper = new MimeMessageHelper(message, true); // Usa MimeMessageHelper para suportar anexos

        // Define o destinatário, o assunto e o corpo do e-mail
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true); // Habilita HTML no corpo do e-mail

        // Envia o e-mail
        javaMailSender.send(message);
        
        // Registra no log que o e-mail foi enviado com sucesso
        log.info("Email sent to: {}", to);
    }

}
