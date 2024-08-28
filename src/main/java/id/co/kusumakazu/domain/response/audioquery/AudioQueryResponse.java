package id.co.kusumakazu.domain.response.audioquery;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class AudioQueryResponse {

    @JsonProperty("pauseLengthScale")
    private int pauseLengthScale;

    @JsonProperty("postPhonemeLength")
    private double postPhonemeLength;

    @JsonProperty("intonationScale")
    private int intonationScale;

    @JsonProperty("pauseLength")
    private Object pauseLength;

    @JsonProperty("pitchScale")
    private int pitchScale;

    @JsonProperty("outputSamplingRate")
    private int outputSamplingRate;

    @JsonProperty("volumeScale")
    private int volumeScale;

    @JsonProperty("kana")
    private String kana;

    @JsonProperty("accent_phrases")
    private List<AccentPhrasesItem> accent_phrases;

    @JsonProperty("speedScale")
    private int speedScale;

    @JsonProperty("outputStereo")
    private boolean outputStereo;

    @JsonProperty("prePhonemeLength")
    private double prePhonemeLength;

    public void setPauseLengthScale(int pauseLengthScale) {
        this.pauseLengthScale = pauseLengthScale;
    }

    public int getPauseLengthScale() {
        return pauseLengthScale;
    }

    public void setPostPhonemeLength(double postPhonemeLength) {
        this.postPhonemeLength = postPhonemeLength;
    }

    public double getPostPhonemeLength() {
        return postPhonemeLength;
    }

    public void setIntonationScale(int intonationScale) {
        this.intonationScale = intonationScale;
    }

    public int getIntonationScale() {
        return intonationScale;
    }

    public void setPauseLength(Object pauseLength) {
        this.pauseLength = pauseLength;
    }

    public Object getPauseLength() {
        return pauseLength;
    }

    public void setPitchScale(int pitchScale) {
        this.pitchScale = pitchScale;
    }

    public int getPitchScale() {
        return pitchScale;
    }

    public void setOutputSamplingRate(int outputSamplingRate) {
        this.outputSamplingRate = outputSamplingRate;
    }

    public int getOutputSamplingRate() {
        return outputSamplingRate;
    }

    public void setVolumeScale(int volumeScale) {
        this.volumeScale = volumeScale;
    }

    public int getVolumeScale() {
        return volumeScale;
    }

    public void setKana(String kana) {
        this.kana = kana;
    }

    public String getKana() {
        return kana;
    }

    public void setAccentPhrases(List<AccentPhrasesItem> accent_phrases) {
        this.accent_phrases = accent_phrases;
    }

    public List<AccentPhrasesItem> getAccentPhrases() {
        return accent_phrases;
    }

    public void setSpeedScale(int speedScale) {
        this.speedScale = speedScale;
    }

    public int getSpeedScale() {
        return speedScale;
    }

    public void setOutputStereo(boolean outputStereo) {
        this.outputStereo = outputStereo;
    }

    public boolean isOutputStereo() {
        return outputStereo;
    }

    public void setPrePhonemeLength(double prePhonemeLength) {
        this.prePhonemeLength = prePhonemeLength;
    }

    public double getPrePhonemeLength() {
        return prePhonemeLength;
    }

    @Override
    public String toString() {
        return (
            "AudioQueryResponse{" +
            "pauseLengthScale = '" +
            pauseLengthScale +
            '\'' +
            ",postPhonemeLength = '" +
            postPhonemeLength +
            '\'' +
            ",intonationScale = '" +
            intonationScale +
            '\'' +
            ",pauseLength = '" +
            pauseLength +
            '\'' +
            ",pitchScale = '" +
            pitchScale +
            '\'' +
            ",outputSamplingRate = '" +
            outputSamplingRate +
            '\'' +
            ",volumeScale = '" +
            volumeScale +
            '\'' +
            ",kana = '" +
            kana +
            '\'' +
            ",accent_phrases = '" +
            accent_phrases +
            '\'' +
            ",speedScale = '" +
            speedScale +
            '\'' +
            ",outputStereo = '" +
            outputStereo +
            '\'' +
            ",prePhonemeLength = '" +
            prePhonemeLength +
            '\'' +
            "}"
        );
    }
}
