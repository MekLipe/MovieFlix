package br.com.movieflix.movieflix.mapper;

import br.com.movieflix.movieflix.controller.request.StreamingRequest;
import br.com.movieflix.movieflix.controller.response.StreamingResponse;
import br.com.movieflix.movieflix.entity.Streaming;
import lombok.experimental.UtilityClass;

@UtilityClass
public class StreamingMapper {

    public static Streaming toStreamingEntity(StreamingRequest streaming_request){
        return Streaming
                .builder()
                .name(streaming_request.name())
                .build();
    }

    public static StreamingResponse toStreamingResponse(Streaming streaming){
        return StreamingResponse
                .builder()
                .id(streaming.getId())
                .name(streaming.getName())
                .build();
    }
}
