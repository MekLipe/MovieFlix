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
        // Deixamos sob responsabilidade do service receber as listas dos objetos completos sem o ID apenas,
        // assim, o mapper vai receber as listas com os objetos, nao apenas com o ID
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

    public List<MovieResponse> ListarFilmesPorCategoria(List<Long> categoriesID){
       List<Movie> movies = movie_repository.findByCategoriesIdIn(categoriesID);
       return movies.stream()
               .map(MovieMapper::toMovieResponse)
               .toList();
    }

    public Optional<MovieResponse> Atualizar(Long id, MovieRequest request){
        Optional<Movie> movie_encontrado = movie_repository.findById(id);

        if (movie_encontrado.isPresent()){
            List<Category> categories = category_service.EncontrarCategorias(request.categories());
            List<Streaming> streamings = streaming_service.EncontrarStreamings(request.streamings());

            Movie movie_alterar = movie_encontrado.get();
            movie_alterar.setTitle(request.title());
            movie_alterar.setDescription(request.description());
            movie_alterar.setRelease_date(request.release_date());
            movie_alterar.setRating(request.rating());

            // clear e addAll faz o Hibernate entender "remova as relações antigas
            //e adicione as novas", fazendo com que as coleções sejam gerenciadas com maior segurança
            movie_alterar.getCategories().clear();
            movie_alterar.getCategories().addAll(categories);

            movie_alterar.getStreamings().clear();
            movie_alterar.getStreamings().addAll(streamings);

            movie_repository.save(movie_alterar);

            return Optional.of(
                    MovieMapper.toMovieResponse(movie_alterar)
            );
        }
        return Optional.empty();
    }

    public boolean DeletarFilme(Long id){
        if (movie_repository.existsById(id)){
            movie_repository.deleteById(id);
            return true;
        }
        return false;
    }
}
