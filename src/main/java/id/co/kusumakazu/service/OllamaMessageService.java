package id.co.kusumakazu.service;

import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface OllamaMessageService {
    String messageRequest(String message, Long sessionId) throws Exception;

    Mono<ResponseEntity<byte[]>> sendFreakyAIMessageWithSynthesize(String message, Integer speaker, Long sessionId) throws Exception;
}
