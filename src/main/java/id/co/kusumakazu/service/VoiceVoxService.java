package id.co.kusumakazu.service;

import id.co.kusumakazu.domain.response.speaker.SpeakersResponse;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface VoiceVoxService {
    SpeakersResponse getSpeaker() throws Exception;
    Mono<ResponseEntity<byte[]>> audioQueryAndSynthesize(String text, Integer speaker) throws Exception;
}
