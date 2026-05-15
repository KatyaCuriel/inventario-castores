package com.castores.inventario.service;

import com.castores.inventario.model.Usuario;
import com.castores.inventario.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String correo)
            throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository
                .findByCorreo(correo)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Usuario no encontrado"));

        return new User(
                usuario.getCorreo(),
                usuario.getPassword(),
                List.of(
                        new SimpleGrantedAuthority(
                                "ROLE_" + usuario.getRol().getNombre()
                        )
                )
        );
    }
}