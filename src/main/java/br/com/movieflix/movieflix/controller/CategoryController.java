package br.com.movieflix.movieflix.controller;

import br.com.movieflix.movieflix.controller.request.CategoryRequest;
import br.com.movieflix.movieflix.controller.response.CategoryResponse;
import br.com.movieflix.movieflix.entity.Category;
import br.com.movieflix.movieflix.mapper.CategoryMapper;
import br.com.movieflix.movieflix.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController()
@RequestMapping("/movieflix/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService category_service;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> ListarCategorias() {
        List<CategoryResponse> categories = category_service.ListarTodos()
                .stream()
                .map(CategoryMapper::toCategoryResponse)
                .toList();

        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> ListarCategoriaPorId(@PathVariable Long id) {
        return category_service.ListarPorId(id)
                .map(category -> ResponseEntity.status(HttpStatus.FOUND).body(CategoryMapper.toCategoryResponse(category)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> CriarCategoria(@RequestBody CategoryRequest request) {
        Category nova_categoria = CategoryMapper.toCategoryEntity(request);
        Category categoria_salva = category_service.CriarCategoria(nova_categoria);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CategoryMapper.toCategoryResponse(categoria_salva));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> DeletarCategoria(@PathVariable Long id) {
        category_service.DeletarCategoria(id);
        return ResponseEntity.ok().build();
    }
}
