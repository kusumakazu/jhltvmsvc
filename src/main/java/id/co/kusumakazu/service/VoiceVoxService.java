package id.co.kusumakazu.service;

import id.co.kusumakazu.domain.AudioQuery;
import id.co.kusumakazu.domain.response.speaker.SpeakersResponse;
import org.springframework.core.io.Resource;

public interface VoiceVoxService {
    SpeakersResponse getSpeaker() throws Exception;
    Resource synthesizeTts(AudioQuery audioQuery) throws Exception;
}
