package br.com.movieflix.movieflix.controller;

import br.com.movieflix.movieflix.controller.request.StreamingRequest;
import br.com.movieflix.movieflix.controller.response.StreamingResponse;
import br.com.movieflix.movieflix.entity.Streaming;
import br.com.movieflix.movieflix.mapper.StreamingMapper;
import br.com.movieflix.movieflix.service.StreamingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movieflix/streaming")
@RequiredArgsConstructor
public class StreamingController {

    private final StreamingService streaming_service;

    @GetMapping
    public ResponseEntity<List<StreamingResponse>> ListarStreamings() {
        List<StreamingResponse> streamings = streaming_service.ListarTodos()
                .stream()
                .map(StreamingMapper::toStreamingResponse)
                .toList();

        return ResponseEntity.ok(streamings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StreamingResponse> ListarStreamingPorId(@PathVariable Long id) {
        return streaming_service.ListarPorId(id)
                .map(streaming -> ResponseEntity.status(HttpStatus.FOUND).body(StreamingMapper.toStreamingResponse(streaming)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<StreamingResponse> CriarStreaming(@RequestBody StreamingRequest request) {
        Streaming novo_streaming = StreamingMapper.toStreamingEntity(request);
        Streaming streaming_salvo = streaming_service.CriarStreaming(novo_streaming);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(StreamingMapper.toStreamingResponse(streaming_salvo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> DeletarStreaming(@PathVariable Long id) {
        streaming_service.DeletarStreaming(id);
        return ResponseEntity.ok().build();
    }
}
