package id.co.kusumakazu.web.rest;

import id.co.kusumakazu.domain.AudioQuery;
import id.co.kusumakazu.domain.response.speaker.SpeakersResponse;
import id.co.kusumakazu.service.VoiceVoxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class VoiceVoxController {

    private final Logger log = LoggerFactory.getLogger(CustomApiResource.class);

    @Autowired
    private VoiceVoxService voiceVoxService;

    @GetMapping("/voice-vox/test")
    public ResponseEntity<SpeakersResponse> getSpeakers() throws Exception {
        log.debug("REST request to get test");
        SpeakersResponse response = voiceVoxService.getSpeaker();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/voice-vox/synthesize")
    public ResponseEntity<Resource> synthesize(@RequestBody AudioQuery audioQuery) throws Exception {
        Resource audioFile = voiceVoxService.synthesizeTts(audioQuery);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "audio/wav").body(audioFile);
    }
}
