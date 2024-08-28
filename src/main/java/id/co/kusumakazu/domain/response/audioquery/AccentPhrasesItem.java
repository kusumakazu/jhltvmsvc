package id.co.kusumakazu.domain.response.audioquery;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class AccentPhrasesItem {

    @JsonProperty("is_interrogative")
    private boolean is_interrogative;

    @JsonProperty("moras")
    private List<MorasItem> moras;

    @JsonProperty("pause_mora")
    private Object pause_mora;

    @JsonProperty("accent")
    private int accent;

    public void setIsInterrogative(boolean is_interrogative) {
        this.is_interrogative = is_interrogative;
    }

    public boolean isIsInterrogative() {
        return is_interrogative;
    }

    public void setMoras(List<MorasItem> moras) {
        this.moras = moras;
    }

    public List<MorasItem> getMoras() {
        return moras;
    }

    public void setPauseMora(Object pause_mora) {
        this.pause_mora = pause_mora;
    }

    public Object getPauseMora() {
        return pause_mora;
    }

    public void setAccent(int accent) {
        this.accent = accent;
    }

    public int getAccent() {
        return accent;
    }

    @Override
    public String toString() {
        return (
            "AccentPhrasesItem{" +
            "is_interrogative = '" +
            is_interrogative +
            '\'' +
            ",moras = '" +
            moras +
            '\'' +
            ",pause_mora = '" +
            pause_mora +
            '\'' +
            ",accent = '" +
            accent +
            '\'' +
            "}"
        );
    }
}
