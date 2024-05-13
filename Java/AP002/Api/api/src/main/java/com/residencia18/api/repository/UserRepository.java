package com.residencia18.api.repository;

import com.residencia18.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repositório para operações de banco de dados relacionadas à entidade User
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Método para encontrar um usuário pelo endereço de e-mail
    User findByEmail(String email);

    // Método para encontrar um usuário pelo nome de usuário
    User findByUsername(String username);
}
