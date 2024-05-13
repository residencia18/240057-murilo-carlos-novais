package com.residencia18.api.exception;

// Exceção lançada quando um nome de usuário já está registrado no sistema
public class UsernameAlreadyRegisteredException extends RuntimeException {
    
    // Construtor padrão que define a mensagem de erro
    public UsernameAlreadyRegisteredException() {
        super("Username already registered!"); // Define a mensagem de erro padrão
    }
}
