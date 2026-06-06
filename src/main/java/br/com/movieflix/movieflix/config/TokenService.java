package br.com.movieflix.movieflix.config;

import br.com.movieflix.movieflix.entity.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Component
public class TokenService {

    @Value("${movieflix.security.secret}")
    private String secret;

    public String GenerateToken(Usuario usuario){
        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create()
                .withSubject(usuario.getEmail())
                .withClaim("userId", usuario.getId())
                .withClaim("nome", usuario.getNome())
                .withExpiresAt(Instant.now().plusSeconds(86400))
                .withIssuedAt(Instant.now())
                .withIssuer("API movieflix")
                .sign(algorithm);
    }

    public Optional<JWTDadosUsuario> VerifyToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);

            DecodedJWT jwt = JWT.require(algorithm)
                    .build()
                    .verify(token);

            return Optional.of(JWTDadosUsuario.builder()
                    .id(jwt.getClaim("userId").asLong())
                    .nome(jwt.getClaim("nome").asString())
                    .email(jwt.getSubject())
                    .build());

        } catch (JWTVerificationException ex){
            return Optional.empty();
        }
    }

}
