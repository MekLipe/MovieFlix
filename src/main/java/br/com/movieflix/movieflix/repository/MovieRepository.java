package br.com.movieflix.movieflix.repository;

import br.com.movieflix.movieflix.entity.Movie;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
