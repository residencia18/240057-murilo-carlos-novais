package com.residencia18.api.exception;

// Exceção lançada quando ocorre um erro ao verificar um token JWT
public class VerifyingTokenException extends RuntimeException {
    
    // Construtor que define a mensagem de erro com base na mensagem fornecida
    public VerifyingTokenException(String message) {
        super("Error verifying tokenJWT:" + message); // Define a mensagem de erro com base na mensagem fornecida
    }
}
