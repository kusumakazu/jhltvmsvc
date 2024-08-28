package id.co.kusumakazu.service.impl;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import id.co.kusumakazu.domain.response.speaker.SpeakersResponse;
import id.co.kusumakazu.domain.response.speaker.SpeakersResponseItem;
import id.co.kusumakazu.service.VoiceVoxService;
import id.co.kusumakazu.web.rest.RestClient;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class VoiceVoxServiceImpl implements VoiceVoxService {

    private final Logger log = LoggerFactory.getLogger(VoiceVoxServiceImpl.class);

    @Autowired
    private RestClient restClient;

    @Override
    public SpeakersResponse getSpeaker() throws Exception {
        log.info("get speaker");
        Gson gson = new Gson();
        List<SpeakersResponseItem> speakersResponses = gson.fromJson(
            restClient.sendGetSpeakers(),
            new TypeToken<List<SpeakersResponseItem>>() {}.getType()
        );

        return new SpeakersResponse(speakersResponses);
    }

    public Mono<ResponseEntity<byte[]>> audioQueryAndSynthesize(String text, Integer speaker) throws Exception {
        log.info("Process Query Creation Audio Query");

        return restClient.sendAudioQueryAndSynthesize(text, speaker);
    }
}
