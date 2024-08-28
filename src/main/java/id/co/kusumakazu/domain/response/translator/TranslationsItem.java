package id.co.kusumakazu.domain.response.translator;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TranslationsItem {

    @JsonProperty("detected_source_language")
    private String detected_source_language;

    @JsonProperty("text")
    private String text;

    public void setDetectedSourceLanguage(String detected_source_language) {
        this.detected_source_language = detected_source_language;
    }

    public String getDetectedSourceLanguage() {
        return detected_source_language;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "TranslationsItem{" + "detected_source_language = '" + detected_source_language + '\'' + ",text = '" + text + '\'' + "}";
    }
}
