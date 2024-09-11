package id.co.kusumakazu.ai.ollama.model;

import org.springframework.ai.model.ChatModelDescription;

public enum OllamaModelExtend implements ChatModelDescription {
    HERMES3("hermes3"),
    LLAMA2_UNCENSORED("llama2-uncensored"),
    WIZARD_VICUNA_UNCENSORED("wizard-vicuna-uncensored");

    private final String id;

    OllamaModelExtend(String id) {
        this.id = id;
    }

    public String id() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.id;
    }
}
