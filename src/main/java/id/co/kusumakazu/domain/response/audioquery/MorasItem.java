package id.co.kusumakazu.domain.response.audioquery;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MorasItem {

    @JsonProperty("consonant")
    private String consonant;

    @JsonProperty("vowel")
    private String vowel;

    @JsonProperty("text")
    private String text;

    @JsonProperty("pitch")
    private double pitch;

    @JsonProperty("consonant_length")
    private double consonant_length;

    @JsonProperty("vowel_length")
    private double vowel_length;

    public void setConsonant(String consonant) {
        this.consonant = consonant;
    }

    public String getConsonant() {
        return consonant;
    }

    public void setVowel(String vowel) {
        this.vowel = vowel;
    }

    public String getVowel() {
        return vowel;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setPitch(double pitch) {
        this.pitch = pitch;
    }

    public double getPitch() {
        return pitch;
    }

    public void setConsonantLength(double consonant_length) {
        this.consonant_length = consonant_length;
    }

    public double getConsonantLength() {
        return consonant_length;
    }

    public void setVowelLength(double vowel_length) {
        this.vowel_length = vowel_length;
    }

    public double getVowelLength() {
        return vowel_length;
    }

    @Override
    public String toString() {
        return (
            "MorasItem{" +
            "consonant = '" +
            consonant +
            '\'' +
            ",vowel = '" +
            vowel +
            '\'' +
            ",text = '" +
            text +
            '\'' +
            ",pitch = '" +
            pitch +
            '\'' +
            ",consonant_length = '" +
            consonant_length +
            '\'' +
            ",vowel_length = '" +
            vowel_length +
            '\'' +
            "}"
        );
    }
}
