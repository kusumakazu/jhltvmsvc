package id.co.kusumakazu.web.rest;

import id.co.kusumakazu.config.ApplicationProperties;
import id.co.kusumakazu.domain.staticconstant.Utils;
import id.co.kusumakazu.service.KatakanaConverterService;
import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@RestController
public class RestClient {

    private final Logger log = LoggerFactory.getLogger(RestClient.class);

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private KatakanaConverterService katakanaConverterService;

    @Autowired
    private WebClient webClient;

    private RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        jsonConverter.setDefaultCharset(StandardCharsets.UTF_8);
        restTemplate.getMessageConverters().add(jsonConverter);

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
                throw new Exception("error when call probably forbidden or unauthorized?, status code :" + resp.getStatusCode());
            }
            return resp.getBody();
        } catch (RestClientResponseException e) {
            log.error(e.getMessage());
            return String.valueOf(ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString()));
        }
    }

    @Deprecated
    public ResponseEntity<String> sendQueryCreationAudioQuery(String text, Integer speaker) throws Exception {
        try {
            log.info("sendQueryCreationAudioQuery ");
            String audioQueryUrl =
                "http://" + applicationProperties.getVoiceVox().getHost() + ":" + applicationProperties.getVoiceVox().getPort();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String audioQueryPayload = UriComponentsBuilder.fromHttpUrl(audioQueryUrl)
                .path(Utils.URL_AUDIO_QUERY)
                .queryParam("text", katakanaConverterService.convert(text))
                .queryParam("speaker", speaker)
                .toUriString();

            log.info("audioQueryPayload : {}", audioQueryPayload);

            HttpEntity<String> audioQueryEntity = new HttpEntity<>(headers);

            ResponseEntity<String> audioQueryResponse = getRestTemplate().postForEntity(audioQueryPayload, audioQueryEntity, String.class);

            if (audioQueryResponse.getStatusCode().is2xxSuccessful()) {
                return audioQueryResponse;
            } else {
                throw new Exception("Failed to send audio query... Response code: " + audioQueryResponse.getStatusCode().value());
            }
        } catch (RestClientResponseException e) {
            log.error("sendQueryCreationAudioQuery : {}", e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public Mono<ResponseEntity<byte[]>> sendAudioQueryAndSynthesize(String text, Integer speaker) {
        return webClient
            .post()
            .uri(uriBuilder -> uriBuilder.path(Utils.URL_AUDIO_QUERY).queryParam("text", text).queryParam("speaker", speaker).build())
            .retrieve()
            .bodyToMono(String.class)
            .flatMap(
                audioQueryResponse ->
                    webClient
                        .post()
                        .uri(
                            uriBuilder ->
                                uriBuilder
                                    .path(Utils.URL_SYNTHESIS)
                                    .queryParam("speaker", speaker)
                                    .queryParam("enable_interrogative_upspeak", true)
                                    .build()
                        )
                        .bodyValue(audioQueryResponse)
                        .retrieve()
                        .bodyToMono(byte[].class)
                        .map(this::createWavResponseEntity)
            );
    }

    private ResponseEntity<byte[]> createWavResponseEntity(byte[] wavContent) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "audio/wav");
        headers.add("Content-Disposition", "attachment; filename=\"output.wav\"");
        return new ResponseEntity<>(wavContent, headers, HttpStatus.OK);
    }
}
