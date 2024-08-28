package id.co.kusumakazu.domain.response.translator;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class DeepLXTranslatorResponse {

    @JsonProperty("translations")
    private List<TranslationsItem> translations;

    public List<TranslationsItem> getTranslations() {
        return translations;
    }

    public void setTranslations(List<TranslationsItem> translations) {
        this.translations = translations;
    }

    @Override
    public String toString() {
        return "DeepLXTranslatorResponse{" + "translations=" + translations + '}';
    }
}
