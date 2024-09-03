package id.co.kusumakazu.domain.response.translator;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeepLXTranslatorResponse that = (DeepLXTranslatorResponse) o;
        return Objects.equals(translations, that.translations);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(translations);
    }
}
