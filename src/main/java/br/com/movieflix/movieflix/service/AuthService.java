package br.com.movieflix.movieflix.service;

import br.com.movieflix.movieflix.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final UsuarioRepository usuario_repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuario_repository.findUsuarioByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario ou senha inválida"));
    }
}
