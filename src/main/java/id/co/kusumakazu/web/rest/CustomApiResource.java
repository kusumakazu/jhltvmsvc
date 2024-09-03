package id.co.kusumakazu.web.rest;

import id.co.kusumakazu.config.ChatConfig;
import id.co.kusumakazu.domain.JPVoice;
import id.co.kusumakazu.domain.staticconstant.Utils;
import id.co.kusumakazu.service.OllamaMessageService;
import id.co.kusumakazu.service.VoiceVoxService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class CustomApiResource {

    private final Logger log = LoggerFactory.getLogger(CustomApiResource.class);

    @Autowired
    private VoiceVoxService voiceVoxService;

    //    @Autowired
    //    private ChatClient chatClient;

    //    @Autowired
    //    public CustomApiResource(ChatClient.Builder builder) {
    //        this.chatClient = builder.build();
    //    }

    @Autowired
    private ChatConfig chatConfig;

    @Autowired
    private OllamaMessageService ollamaMessageService;

    @GetMapping("/test/prompt")
    public ResponseEntity<String> prompt(@RequestParam String message) {
        log.debug("REST request to get test prompt : {}", message);

        var ollamaApi = new OllamaApi();

        var chatModel = new OllamaChatModel(ollamaApi, OllamaOptions.create().withModel("llama3").withTemperature(0.9f));

        PromptTemplate promptTemplate = new PromptTemplate(message);
        Prompt prompt = promptTemplate.create();

        //        ChatResponse response = chatClient.prompt(prompt).call()
        //            .chatResponse();
        ChatResponse response = chatModel.call(prompt);
        log.info("response : {}", response.getResult().getOutput().getContent());

        return ResponseEntity.ok(response.getResult().getOutput().getContent());
    }

    @GetMapping("/test/prompt2")
    public ResponseEntity<String> prompt2(@RequestParam String message) {
        log.debug("REST request to get test prompt 2: {}", message);
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
            .withKeepAlive("10m")
            .build();

        log.info("request : {}", request.messages().get(1));

        OllamaApi.ChatResponse response = ollamaApi.chat(request);

        log.info("Response : {}", response);
        return ResponseEntity.ok(response.message().content());
    }

    @GetMapping("/freaky-ai/chat")
    public Mono<ResponseEntity<byte[]>> freakyChatting(@RequestParam String text, @RequestParam JPVoice speaker) throws Exception {
        log.debug("REST request to freaky Chatting with ai : {}", text);
        long start = System.currentTimeMillis();

        Mono<ResponseEntity<byte[]>> response = ollamaMessageService.sendFreakyAIMessageWithSynthesize(text, speaker.getVoiceId());

        log.info("total time to process : " + (System.currentTimeMillis() - start) + " in ms");
        return response;
    }
}
