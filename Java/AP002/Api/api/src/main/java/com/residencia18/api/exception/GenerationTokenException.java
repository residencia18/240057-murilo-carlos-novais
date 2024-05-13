package com.residencia18.api.exception;

// Exceção lançada quando ocorre um erro ao gerar um token JWT
public class GenerationTokenException extends RuntimeException {
    
    // Construtor que define a mensagem de erro com base na mensagem fornecida
    public GenerationTokenException(String message) {
        super("Error generating tokenJWT: " + message); // Define a mensagem de erro com base na mensagem fornecida
    }
}
