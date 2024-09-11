package id.co.kusumakazu.service.impl;

import id.co.kusumakazu.ai.ollama.model.OllamaModelExtend;
import id.co.kusumakazu.domain.staticconstant.Utils;
import id.co.kusumakazu.service.AiSessionService;
import id.co.kusumakazu.service.OllamaMessageService;
import id.co.kusumakazu.service.SessionMessageService;
import id.co.kusumakazu.service.VoiceVoxService;
import id.co.kusumakazu.service.dto.AiSessionDTO;
import id.co.kusumakazu.service.dto.SessionMessageDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    private final Logger log = LoggerFactory.getLogger(OllamaMessageServiceImpl.class);

    @Autowired
    private VoiceVoxService voiceVoxService;

    @Autowired
    private AiSessionService aiSessionService;

    @Autowired
    private SessionMessageService sessionMessageService;

    @Override
    public String messageRequest(String message, Long sessionId) throws Exception {
        log.info("Process prompting message Request to ollama");
        var ollamaApi = new OllamaApi();
        var request = OllamaApi.ChatRequest.builder(OllamaModelExtend.HERMES3.id())
            .withStream(false)
            .withMessages(messageHistory(message, sessionId))
            .withOptions(OllamaOptions.create().withTemperature(0.9f))
            .withKeepAlive("5m")
            .build();

        OllamaApi.ChatResponse response = ollamaApi.chat(request);
        sessionMessageService.save(
            new SessionMessageDTO(null, OllamaApi.Message.Role.ASSISTANT.name(), response.message().content(), sessionId)
        );

        return response.message().content();
    }

    @Override
    public Mono<ResponseEntity<byte[]>> sendFreakyAIMessageWithSynthesize(String message, Integer speaker, Long sessionId)
        throws Exception {
        log.info("Processing prompting and Synthesize audio to JP");

        return voiceVoxService.audioQueryAndSynthesize(messageRequest(message, sessionId), speaker);
    }

    private List<OllamaApi.Message> messageHistory(String message, Long sessionId) throws Exception {
        List<OllamaApi.Message> messageList = new ArrayList<>();
        Optional<AiSessionDTO> aiSessionDTOOptional = aiSessionService.findOne(sessionId);
        AiSessionDTO sessionDTO;
        if (aiSessionDTOOptional.isPresent()) {
            sessionDTO = aiSessionDTOOptional.get();
            List<SessionMessageDTO> messageDTOList = sessionMessageService.findAllByAiSessionId(sessionDTO.getId());
            log.info("SessionMessageDTO size : {}", messageDTOList.size());
            if (messageDTOList.isEmpty()) {
                messageList.add(OllamaApi.Message.builder(OllamaApi.Message.Role.SYSTEM).withContent(Utils.FREAKY_SYSTEM_PROMPT).build());
                messageList.add(OllamaApi.Message.builder(OllamaApi.Message.Role.USER).withContent(message).build());
                sessionMessageService.save(new SessionMessageDTO(null, "user", message, sessionDTO.getId()));
            } else {
                messageList.add(OllamaApi.Message.builder(OllamaApi.Message.Role.SYSTEM).withContent(Utils.FREAKY_SYSTEM_PROMPT).build());
                for (SessionMessageDTO data : messageDTOList) {
                    if (data.getRole().contentEquals("user")) messageList.add(
                        OllamaApi.Message.builder(OllamaApi.Message.Role.USER).withContent(data.getContent()).build()
                    );
                    else if (data.getRole().contentEquals("ASSISTANT")) messageList.add(
                        OllamaApi.Message.builder(OllamaApi.Message.Role.ASSISTANT).withContent(data.getContent()).build()
                    );
                }
                messageList.add(OllamaApi.Message.builder(OllamaApi.Message.Role.SYSTEM).withContent(Utils.AI_LAST_HISTORY_PROMT).build());
                messageList.add(
                    OllamaApi.Message.builder(OllamaApi.Message.Role.ASSISTANT)
                        .withContent(messageDTOList.get(messageDTOList.size() - 2).getContent())
                        .build()
                );
                messageList.add(OllamaApi.Message.builder(OllamaApi.Message.Role.USER).withContent(message).build());
                sessionMessageService.save(new SessionMessageDTO(null, "user", message, sessionDTO.getId()));
            }
        } else if (aiSessionDTOOptional.isEmpty()) {
            throw new Exception("Session Not Found... please create a session first!");
        }

        return messageList;
    }
}
