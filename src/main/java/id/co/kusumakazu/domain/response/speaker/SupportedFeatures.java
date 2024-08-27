package id.co.kusumakazu.domain.response.speaker;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SupportedFeatures {

    @JsonProperty("permitted_synthesis_morphing")
    private String permitted_synthesis_morphing;

    public void setPermittedSynthesisMorphing(String permitted_synthesis_morphing) {
        this.permitted_synthesis_morphing = permitted_synthesis_morphing;
    }

    public String getPermittedSynthesisMorphing() {
        return permitted_synthesis_morphing;
    }

    @Override
    public String toString() {
        return "SupportedFeatures{" + "permitted_synthesis_morphing = '" + permitted_synthesis_morphing + '\'' + "}";
    }
}
