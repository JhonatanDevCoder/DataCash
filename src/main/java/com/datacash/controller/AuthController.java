package com.datacash.controller;

import com.datacash.dto.LoginRequest;
import com.datacash.dto.LoginResponse;
import com.datacash.model.Mes;
import com.datacash.model.Usuario;
import com.datacash.repository.MesRepository;
import com.datacash.repository.UsuarioRepository;
import com.datacash.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MesRepository mesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@RequestBody com.datacash.dto.RegistroRequest registroRequest) {
        try {
            Usuario usuario = usuarioService.registrarUsuario(registroRequest);
            Map<String, Object> response = Map.of(
                "id", usuario.getId(),
                "message", "Usuario registrado con éxito."
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(loginRequest.getEmail());

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", "Usuario no encontrado con el email: " + loginRequest.getEmail()));
        }

        Usuario usuario = usuarioOpt.get();
        if (!passwordEncoder.matches(loginRequest.getPassword(), usuario.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", "Contraseña incorrecta"));
        }
        
        LocalDate hoy = LocalDate.now();
        
        Mes mesActual = mesRepository.findByUsuarioAndAnioAndMes(usuario, hoy.getYear(), hoy.getMonthValue())
            .orElseGet(() -> {
                Mes nuevoMes = new Mes();
                nuevoMes.setUsuario(usuario);
                nuevoMes.setAnio(hoy.getYear());
                nuevoMes.setMes(hoy.getMonthValue());
                return mesRepository.save(nuevoMes);
            });
            
        LoginResponse response = new LoginResponse(
            "Inicio de sesión exitoso.",
            usuario.getId(),
            usuario.getNombre(),
            usuario.getRol(),
            mesActual.getId(),
            mesActual.getLimiteGasto()
        );

        return ResponseEntity.ok(response);
    }
}