package id.co.kusumakazu.service.impl;

import id.co.kusumakazu.domain.staticconstant.Utils;
import id.co.kusumakazu.service.OllamaMessageService;
import id.co.kusumakazu.service.VoiceVoxService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class OllamaMessageServiceImpl implements OllamaMessageService {

    private final Logger log = LoggerFactory.getLogger(OllamaMessageService.class);

    @Autowired
    private VoiceVoxService voiceVoxService;

    @Override
    public String messageRequest(String message) {
        log.info("Process prompting message Request to ollama");
        var ollamaApi = new OllamaApi();
        var request = OllamaApi.ChatRequest.builder("llama3")
            .withStream(false)
            .withMessages(
                List.of(
                    OllamaApi.Message.builder(OllamaApi.Message.Role.SYSTEM).withContent(Utils.FREAKY_SYSTEM_PROMPT).build(),
                    OllamaApi.Message.builder(OllamaApi.Message.Role.USER).withContent(message).build()
                )
            )
            .withOptions(OllamaOptions.create().withTemperature(0.9f))
            .withKeepAlive("5m")
            .build();

        log.info("request : {}", request.messages().get(1));

        OllamaApi.ChatResponse response = ollamaApi.chat(request);
        log.info("Response : {}", response);
        return response.message().content();
    }

    @Override
    public Mono<ResponseEntity<byte[]>> sendFreakyAIMessageWithSynthesize(String message, Integer speaker) throws Exception {
        log.info("Processing prompting and Synthesize audio to JP");

        return voiceVoxService.audioQueryAndSynthesize(messageRequest(message), speaker);
    }
}
