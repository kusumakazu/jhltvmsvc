package id.co.kusumakazu.domain;

public class AudioQuery {

    public String message;
    public Integer speaker;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getSpeaker() {
        return speaker;
    }

    public void setSpeaker(Integer speaker) {
        this.speaker = speaker;
    }

    @Override
    public String toString() {
        return "AudioQuery{" + "message='" + message + '\'' + ", speaker=" + speaker + '}';
    }
}
