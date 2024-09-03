package id.co.kusumakazu.web.rest;

import id.co.kusumakazu.domain.response.speaker.SpeakersResponse;
import id.co.kusumakazu.service.TranslatorService;
import id.co.kusumakazu.service.VoiceVoxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class VoiceVoxController {

    private final Logger log = LoggerFactory.getLogger(VoiceVoxController.class);

    @Autowired
    private VoiceVoxService voiceVoxService;

    @Autowired
    private TranslatorService translatorService;

    @GetMapping("/voice-vox/test")
    public ResponseEntity<SpeakersResponse> getSpeakers() throws Exception {
        log.debug("REST request to get test");
        SpeakersResponse response = voiceVoxService.getSpeaker();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/voice-vox/synthesize")
    public Mono<ResponseEntity<byte[]>> synthesize2(@RequestParam String text, @RequestParam Integer speaker) throws Exception {
        return voiceVoxService.audioQueryAndSynthesize(text, speaker);
    }
}
