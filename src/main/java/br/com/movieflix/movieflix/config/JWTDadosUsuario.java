package br.com.movieflix.movieflix.config;

import lombok.Builder;

@Builder
public record JWTDadosUsuario(Long id, String nome, String email) {
}
