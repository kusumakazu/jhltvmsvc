package id.co.kusumakazu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Autowired
    private ApplicationProperties applicationProperties;

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        int bufferSize = 32 * 1024 * 1024; // 16 MB buffer size

        ExchangeStrategies strategies = ExchangeStrategies.builder()
            .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(bufferSize))
            .build();

        return builder
            .baseUrl("http://" + applicationProperties.getVoiceVox().getHost() + ":" + applicationProperties.getVoiceVox().getPort())
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
    }
}
