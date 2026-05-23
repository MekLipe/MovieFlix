package br.com.movieflix.movieflix.service;

import br.com.movieflix.movieflix.entity.Category;
import br.com.movieflix.movieflix.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository category_repository;

    public List<Category> ListarTodos(){
        return category_repository.findAll();
    }

    public Optional<Category> ListarPorId(Long id){
        return category_repository.findById(id);
    }

    public Category CriarCategoria(Category category){
        return category_repository.save(category);
    }

    public void DeletarCategoria(Long id){
        category_repository.deleteById(id);
    }

    public List<Category> EncontrarCategorias(List<Long> categories){
        return category_repository.findAllById(categories);
    }
}
