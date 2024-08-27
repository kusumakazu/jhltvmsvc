package id.co.kusumakazu.domain.response.speaker;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class SpeakersResponse {

    @JsonProperty("SpeakersResponse")
    private List<SpeakersResponseItem> speakersResponse;

    public SpeakersResponse(List<SpeakersResponseItem> speakersResponse) {
        this.speakersResponse = speakersResponse;
    }

    public List<SpeakersResponseItem> getSpeakersResponse() {
        return speakersResponse;
    }

    public void setSpeakersResponse(List<SpeakersResponseItem> speakersResponse) {
        this.speakersResponse = speakersResponse;
    }

    @Override
    public String toString() {
        return "SpeakersResponse{" + "speakersResponse = '" + speakersResponse + '\'' + "}";
    }
}
