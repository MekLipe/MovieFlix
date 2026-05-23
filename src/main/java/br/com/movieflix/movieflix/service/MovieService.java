package br.com.movieflix.movieflix.service;

import br.com.movieflix.movieflix.controller.request.MovieRequest;
import br.com.movieflix.movieflix.controller.response.MovieResponse;
import br.com.movieflix.movieflix.entity.Category;
import br.com.movieflix.movieflix.entity.Movie;
import br.com.movieflix.movieflix.entity.Streaming;
import br.com.movieflix.movieflix.mapper.MovieMapper;
import br.com.movieflix.movieflix.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private final MovieRepository movie_repository;
    private final CategoryService category_service;
    private final StreamingService streaming_service;

    public MovieService(MovieRepository movie_repository, CategoryService category_service, StreamingService streamingService) {
        this.movie_repository = movie_repository;
        this.category_service = category_service;
        this.streaming_service = streamingService;
    }

    public Movie Criar(MovieRequest request){
        List<Category> categories = category_service.EncontrarCategorias(request.categories());
        List<Streaming> streamings = streaming_service.EncontrarStreamings(request.streamings());
        /*Deixamos sob responsabilidade do service receber as listas dos objetos completos sem o ID apenas,
          assim, o mapper vai receber as listas com os objetos, nao apenas com o ID
        */
        Movie movie_salvo = MovieMapper.toMovieEntity(request, categories, streamings);
        return movie_repository.save(movie_salvo);
    }

    public List<Movie> ListarTodos(){
        return movie_repository.findAll();
    }

    public Optional<MovieResponse> ListarPorID(Long id){
        Optional<Movie> movie_encontrado = movie_repository.findById(id);
        return movie_encontrado.map(MovieMapper::toMovieResponse);
    }

}
