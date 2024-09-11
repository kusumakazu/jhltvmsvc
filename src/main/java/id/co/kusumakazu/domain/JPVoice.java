package id.co.kusumakazu.domain;

import id.co.kusumakazu.domain.staticconstant.Utils;

public enum JPVoice {
    ELDER_SISTER(Utils.JP_ELDER_SISTER_VOICE_ID),
    GIRL_WHISPER(Utils.JP_GIRL_WHISPER_VOICE_ID),
    GIRL_CUTE(Utils.JP_GIRL_CUTE_VOICE_ID),
    GIRL_OJOU(Utils.JP_GIRL_OJOU_VOICE_ID);

    private final Integer voiceId;

    JPVoice(Integer jpElderSisterVoiceId) {
        this.voiceId = jpElderSisterVoiceId;
    }

    public Integer getVoiceId() {
        return voiceId;
    }
}
