package id.co.kusumakazu.domain;

public enum TargetTranslateContext {
    ID("Indonesian"),
    JA("Japanese"),
    EN("English"),
    DE("German"),
    FR("French"),
    ES("Spanish"),
    IT("Italian"),
    KO("Korean"),
    NL("Dutch"),
    TR("Turkish"),
    RU("Russian"),
    ZH("Chinese "),
    AR("Arabic");

    private final String languageName;

    TargetTranslateContext(String languageName) {
        this.languageName = languageName;
    }

    public String getLanguageCode() {
        return this.name();
    }
}
