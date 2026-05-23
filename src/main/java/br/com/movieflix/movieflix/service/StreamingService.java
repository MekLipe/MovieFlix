package br.com.movieflix.movieflix.service;

import br.com.movieflix.movieflix.entity.Streaming;
import br.com.movieflix.movieflix.repository.StreamingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StreamingService {

    private final StreamingRepository streaming_repository;

    public List<Streaming> ListarTodos() {
        return streaming_repository.findAll();
    }

    public Optional<Streaming> ListarPorId(Long id) {
        return streaming_repository.findById(id);
    }

    public Streaming CriarStreaming(Streaming streaming) {
        return streaming_repository.save(streaming);
    }

    public void DeletarStreaming(Long id) {
        streaming_repository.deleteById(id);
    }

    public List<Streaming> EncontrarStreamings(List<Long> streamings){
        return streaming_repository.findAllById(streamings);
    }
}
