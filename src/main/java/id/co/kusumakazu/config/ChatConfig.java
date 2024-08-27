package id.co.kusumakazu.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ChatConfig {

    @Value("${spring.ai.huggingface.chat.api-key}")
    private String huggingfaceApiKey;

    @Value("${spring.ai.huggingface.chat.url}")
    private String huggingfaceChatUrl;

    public String getHuggingfaceApiKey() {
        return huggingfaceApiKey;
    }

    public String getHuggingfaceChatUrl() {
        return huggingfaceChatUrl;
    }
}
