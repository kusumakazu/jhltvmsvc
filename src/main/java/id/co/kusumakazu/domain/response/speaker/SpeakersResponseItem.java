package id.co.kusumakazu.domain.response.speaker;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class SpeakersResponseItem {

    @JsonProperty("supported_features")
    private SupportedFeatures supported_features;

    @JsonProperty("name")
    private String name;

    @JsonProperty("styles")
    private List<StylesItem> styles;

    @JsonProperty("speaker_uuid")
    private String speaker_uuid;

    @JsonProperty("version")
    private String version;

    public void setSupportedFeatures(SupportedFeatures supported_features) {
        this.supported_features = supported_features;
    }

    public SupportedFeatures getSupportedFeatures() {
        return supported_features;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setStyles(List<StylesItem> styles) {
        this.styles = styles;
    }

    public List<StylesItem> getStyles() {
        return styles;
    }

    public void setSpeakerUuid(String speaker_uuid) {
        this.speaker_uuid = speaker_uuid;
    }

    public String getSpeakerUuid() {
        return speaker_uuid;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return (
            "SpeakersResponseItem{" +
            "supported_features = '" +
            supported_features +
            '\'' +
            ",name = '" +
            name +
            '\'' +
            ",styles = '" +
            styles +
            '\'' +
            ",speaker_uuid = '" +
            speaker_uuid +
            '\'' +
            ",version = '" +
            version +
            '\'' +
            "}"
        );
    }
}
