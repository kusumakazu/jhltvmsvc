package id.co.kusumakazu.web.rest;

import id.co.kusumakazu.config.ApplicationProperties;
import id.co.kusumakazu.domain.AudioQuery;
import id.co.kusumakazu.domain.staticconstant.Utils;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class RestClient {

    private final Logger log = LoggerFactory.getLogger(RestClient.class);

    @Autowired
    private ApplicationProperties applicationProperties;

    private RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate;
    }

    public String sendGetSpeakers() throws Exception {
        try {
            log.info("request sendGetSpeakers");
            String speakerUrl =
                "http://" +
                applicationProperties.getVoiceVox().getHost() +
                ":" +
                applicationProperties.getVoiceVox().getPort() +
                Utils.URL_SPEAKERS;
            long starttry = System.currentTimeMillis();
            ResponseEntity<String> resp = getRestTemplate().exchange(speakerUrl, HttpMethod.GET, HttpEntity.EMPTY, String.class);

            log.info(String.format("length of time get data : %d in ms", System.currentTimeMillis() - starttry));

            log.info("response : {} ", resp.getStatusCode());

            if (resp.getStatusCode().is4xxClientError()) {
                throw new Exception("error when call probably forbidden or unauthorized?, status code :" + resp.getStatusCodeValue());
            }
            return resp.getBody();
        } catch (RestClientResponseException e) {
            log.error(e.getMessage());
            return String.valueOf(ResponseEntity.status(e.getRawStatusCode()).body(e.getResponseBodyAsString()));
        }
    }

    public Resource sendCreateQueryAndSynthesize(AudioQuery audioQuery) throws Exception {
        log.info("sendQueryCreationAudioQuery ");
        try {
            String audioQueryUrl =
                "http://" + applicationProperties.getVoiceVox().getHost() + ":" + applicationProperties.getVoiceVox().getPort();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String audioQueryPayload = UriComponentsBuilder.fromHttpUrl(audioQueryUrl)
                .path(Utils.URL_AUDIO_QUERY)
                .queryParam("text", audioQuery.getMessage())
                .queryParam("speaker", audioQuery.getSpeaker())
                .toUriString();

            log.info("audioQueryPayload : {}", audioQueryPayload);

            HttpEntity<String> audioQueryEntity = new HttpEntity<>(headers);

            ResponseEntity<String> audioQueryResponse = getRestTemplate()
                .exchange(audioQueryPayload, HttpMethod.POST, audioQueryEntity, String.class);
            log.info("audioQueryResponse : {}", audioQueryResponse);
            log.info("Success audio Query");
            String synthesisUrl =
                "http://" + applicationProperties.getVoiceVox().getHost() + ":" + applicationProperties.getVoiceVox().getPort();
            String synthesisPayload = UriComponentsBuilder.fromHttpUrl(synthesisUrl)
                .path(Utils.URL_SYNTHESIS)
                .queryParam("speaker", audioQuery.getSpeaker())
                .queryParam("enable_interrogative_upspeak", true)
                .toUriString();

            HttpEntity<String> synthesisEntity = new HttpEntity<>(audioQueryResponse.getBody(), headers);

            ResponseEntity<byte[]> synthesisResponse = getRestTemplate()
                .exchange(synthesisPayload, HttpMethod.POST, synthesisEntity, byte[].class);

            log.info("Success synthesis");

            InputStream inputStream = new ByteArrayInputStream(synthesisResponse.getBody());
            return new InputStreamResource(inputStream);
        } catch (Exception e) {
            throw new Exception("Error synthesizing TTS", e);
        }
    }
}
