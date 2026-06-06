package br.com.movieflix.movieflix.controller;

import br.com.movieflix.movieflix.config.TokenService;
import br.com.movieflix.movieflix.controller.request.LoginRequest;
import br.com.movieflix.movieflix.controller.request.UsuarioRequest;
import br.com.movieflix.movieflix.controller.response.LoginResponse;
import br.com.movieflix.movieflix.controller.response.UsuarioResponse;
import br.com.movieflix.movieflix.entity.Usuario;
import br.com.movieflix.movieflix.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("movieflix/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService usuario_service;
    private final AuthenticationManager authentication_manager;
    private final TokenService token_service;

    @PostMapping("/register")
    public ResponseEntity<UsuarioResponse> Register(@RequestBody UsuarioRequest request) {
        UsuarioResponse user_salvo = usuario_service.Salvar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user_salvo);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> Login(@RequestBody LoginRequest request){
        UsernamePasswordAuthenticationToken usuario_senha = new UsernamePasswordAuthenticationToken(request.email(), request.senha());
        Authentication authenticate = authentication_manager.authenticate(usuario_senha);

        Usuario usuario = (Usuario) authenticate.getPrincipal();
        String token = token_service.GenerateToken(usuario);

        return ResponseEntity.ok(new LoginResponse(token));
    }
}
