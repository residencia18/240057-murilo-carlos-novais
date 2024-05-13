package com.residencia18.api.exception;

// Exceção lançada quando um endereço de e-mail já está registrado no sistema
public class EmailAlreadyRegisteredException extends RuntimeException {
    // Construtor padrão que define a mensagem de erro
    public EmailAlreadyRegisteredException() {
        super("Email already registered."); // Define a mensagem de erro padrão
    }
}
