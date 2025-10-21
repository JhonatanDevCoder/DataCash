package com.datacash.service;

import com.datacash.dto.RegistroRequest;
import com.datacash.model.Usuario;
import com.datacash.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario registrarUsuario(RegistroRequest registroRequest) {
        if (usuarioRepository.findByEmail(registroRequest.getEmail()).isPresent()) {
            throw new RuntimeException("El correo electrónico ya está en uso.");
        }

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(registroRequest.getNombre());
        nuevoUsuario.setEmail(registroRequest.getEmail());
        nuevoUsuario.setPassword(passwordEncoder.encode(registroRequest.getPassword()));

        return usuarioRepository.save(nuevoUsuario);
    }
}