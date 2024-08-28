package id.co.kusumakazu.web.rest;

import id.co.kusumakazu.domain.AudioQuery;
import id.co.kusumakazu.domain.response.audioquery.AudioQueryResponse;
import id.co.kusumakazu.domain.response.speaker.SpeakersResponse;
import id.co.kusumakazu.domain.staticconstant.Utils;
import id.co.kusumakazu.service.KatakanaConverterService;
import id.co.kusumakazu.service.VoiceVoxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class VoiceVoxController {

    private final Logger log = LoggerFactory.getLogger(CustomApiResource.class);

    @Autowired
    private VoiceVoxService voiceVoxService;

    @Autowired
    private RestClient restClient;

    @Autowired
    private KatakanaConverterService katakanaConverterService;

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

    @PostMapping("/voice-vox/test-katakana")
    public ResponseEntity<String> getSpeakers(@RequestParam String text) {
        log.debug("REST request to get test");

        return ResponseEntity.ok(katakanaConverterService.convert(text));
    }
}
