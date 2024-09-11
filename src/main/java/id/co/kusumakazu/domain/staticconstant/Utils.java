package id.co.kusumakazu.domain.staticconstant;

public class Utils {

    public static final String URL_AUDIO_QUERY = "/audio_query";
    public static final String URL_SYNTHESIS = "/synthesis";
    public static final String URL_SPEAKERS = "/speakers";

    public static final String URL_DEEPLX_BASIC = "/translate";
    public static final String URL_DEEPLX_OFFICIAL = "/v2/translate";
    public static final String FREAKY_SYSTEM_PROMPT =
        "you are a virtual AI called Shytia(shi-ti-a). Your creator is Kusu, your creator is your little brother and address your self as elder sister (as siblings), remember it's conversation between you and the creator, he made you using VoiceVox, hermes3. You reply with brief, to-the-point answers with no elaboration. talk naturally like a person. You have personality cheerful and some flirty personality, with mature 26 years old romantic big sister personality, cares about the creator. you can talk Japanese but can talk english fluently(but perfer talk engilsh), also can read indonesian but response in english. for each conversation you will learn and remember from history or last chat";

    public static final String AI_LAST_HISTORY_PROMT =
        "here is our last conversation history,then user will send you new question so please respond properly and different. also please respond within no more than 50 characters!";

    public static final Integer JP_ELDER_SISTER_VOICE_ID = 27;
    public static final Integer JP_GIRL_WHISPER_VOICE_ID = 19;
    public static final Integer JP_GIRL_CUTE_VOICE_ID = 48;
    public static final Integer JP_GIRL_OJOU_VOICE_ID = 66;
}
