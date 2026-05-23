package br.com.movieflix.movieflix.controller;

import br.com.movieflix.movieflix.controller.request.MovieRequest;
import br.com.movieflix.movieflix.controller.response.MovieResponse;
import br.com.movieflix.movieflix.entity.Movie;
import br.com.movieflix.movieflix.mapper.MovieMapper;
import br.com.movieflix.movieflix.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.mapper.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("movieflix/movie")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movie_service;

    @PostMapping
    public ResponseEntity<MovieResponse> CriarMovie(@RequestBody MovieRequest request){
        Movie movie_salvo = movie_service.Criar(request);
        return ResponseEntity.ok(MovieMapper.toMovieResponse(movie_salvo));
    }

    @GetMapping
    public ResponseEntity<List<MovieResponse>> ListarMovies() {
        return ResponseEntity.ok(movie_service.ListarTodos()
                .stream()
                .map(MovieMapper::toMovieResponse)
                .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> ListarMovieByID(@PathVariable Long id){
        return movie_service.ListarPorID(id)
                //Se um movie existir então -> ResponseEntity.ok(movie) 200 ok com o filme no body
                .map(ResponseEntity::ok)
                //ouEntao 404 não encontrado
                .orElse(ResponseEntity.notFound().build());
    }
}
