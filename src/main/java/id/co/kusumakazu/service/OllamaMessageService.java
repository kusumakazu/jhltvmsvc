package id.co.kusumakazu.service;

import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface OllamaMessageService {
    String messageRequest(String message);

    Mono<ResponseEntity<byte[]>> sendFreakyAIMessageWithSynthesize(String message, Integer speaker) throws Exception;
}
