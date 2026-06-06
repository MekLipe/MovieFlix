package br.com.movieflix.movieflix.controller.response;

import lombok.Builder;

@Builder
public record UsuarioResponse(Long id, String nome, String email) {
}
