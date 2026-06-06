package br.com.movieflix.movieflix.mapper;

import br.com.movieflix.movieflix.controller.request.UsuarioRequest;
import br.com.movieflix.movieflix.controller.response.UsuarioResponse;
import br.com.movieflix.movieflix.entity.Usuario;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UsuarioMapper {

    public static Usuario toUsuarioEntity(UsuarioRequest request){
        return Usuario.builder()
                .nome(request.nome())
                .email(request.email())
                .senha(request.senha())
                .build();
    }

    public static UsuarioResponse toUsuarioResponse(Usuario user){
        return UsuarioResponse.builder()
                .id(user.getId())
                .nome(user.getNome())
                .email(user.getEmail())
                .build();
    }
}
