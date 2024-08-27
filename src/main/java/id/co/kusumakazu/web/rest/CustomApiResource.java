package id.co.kusumakazu.web.rest;

import id.co.kusumakazu.config.ChatConfig;
import id.co.kusumakazu.service.VoiceVoxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.huggingface.HuggingfaceChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CustomApiResource {

    private final Logger log = LoggerFactory.getLogger(CustomApiResource.class);

    @Autowired
    private VoiceVoxService voiceVoxService;

    //        private ChatClient chatClient;
    //    public CustomApiResource(ChatClient.Builder builder) {
    //        this.chatClient = builder.build();
    //    }

    @Autowired
    private ChatConfig chatConfig;

    @GetMapping("/test/prompt")
    public ResponseEntity<String> prompt(@RequestParam String message) {
        log.debug("REST request to get test prompt : {}", message);

        PromptTemplate promptTemplate = new PromptTemplate(message);
        Prompt prompt = promptTemplate.create();

        //        ChatResponse response = chatClient.prompt(prompt).call()
        //                .chatResponse();
        HuggingfaceChatModel chatModel = new HuggingfaceChatModel(chatConfig.getHuggingfaceApiKey(), chatConfig.getHuggingfaceChatUrl());
        ChatResponse response = chatModel.call(prompt);
        return ResponseEntity.ok(response.getResult().getOutput().getContent());
    }
}
