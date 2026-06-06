package br.com.movieflix.movieflix.service;

import br.com.movieflix.movieflix.controller.request.UsuarioRequest;
import br.com.movieflix.movieflix.controller.response.UsuarioResponse;
import br.com.movieflix.movieflix.entity.Usuario;
import br.com.movieflix.movieflix.mapper.UsuarioMapper;
import br.com.movieflix.movieflix.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuario_repository;
    private final PasswordEncoder password_encoder;

    public UsuarioResponse Salvar(UsuarioRequest request){
        Usuario user = UsuarioMapper.toUsuarioEntity(request);
        String senha = request.senha();
        user.setSenha(password_encoder.encode(senha));
        usuario_repository.save(user);
        return UsuarioMapper.toUsuarioResponse(user);
    }
}
