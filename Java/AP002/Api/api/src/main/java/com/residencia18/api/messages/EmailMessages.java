package com.residencia18.api.messages;

import com.residencia18.api.entity.User;

// Classe responsável por fornecer mensagens de e-mail relacionadas à recuperação de senha
public class EmailMessages {
    
    // Título padrão para e-mails de solicitação de recuperação de senha
    public static final String RECOVERY_TITLE = "Solicitação de Redefinição de Senha!";
    
    // Método para gerar a mensagem de e-mail de recuperação de senha
    public static String messageToRecovery(User user, String link){
        return
            "<div>Olá " + user.getUsername() + ", sua solicitação de redefinição de senha foi recebida e está sendo processada.</div>"
            + "<div>Para redefinir sua senha, clique no link abaixo: </div>"
            + "<div><a href='" + link + "'>Redefinir Senha</a></div>"
            + "<div>Se você não solicitou a redefinição de senha, ignore este e-mail.</div>";
    }
}
