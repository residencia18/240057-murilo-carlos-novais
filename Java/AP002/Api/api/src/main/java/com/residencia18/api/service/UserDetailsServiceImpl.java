package com.residencia18.api.service;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.residencia18.api.entity.User;
import com.residencia18.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    // Método para carregar os detalhes do usuário com base no nome de usuário
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Busca o usuário no repositório pelo nome de usuário
        User user = userRepository.findByUsername(username);
        
        // Lança uma exceção se o usuário não for encontrado
        if (user == null) throw new UsernameNotFoundException("User not found");
        
        // Retorna um objeto UserDetails com as informações do usuário encontrado
        return new org.springframework.security
                .core.userdetails.User(user.getUsername(), user.getPassword(),
                true, true, true,
                true, getAuthorities(user));
    }

    // Método para obter as autoridades do usuário
    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        // Retorna uma lista contendo a autoridade do usuário
        return Collections.singletonList(new SimpleGrantedAuthority(user.getRole()));
    }
}
