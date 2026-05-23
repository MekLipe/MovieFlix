package br.com.movieflix.movieflix.repository;

import br.com.movieflix.movieflix.entity.Movie;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    public List<Movie> findByCategoriesIdIn(List<Long> categoryId);
}
