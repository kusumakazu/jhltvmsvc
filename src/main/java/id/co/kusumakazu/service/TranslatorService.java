package id.co.kusumakazu.service;

import id.co.kusumakazu.domain.TargetTranslateContext;
import id.co.kusumakazu.domain.response.translator.DeepLXTranslatorResponse;
import java.util.List;

public interface TranslatorService {
    public DeepLXTranslatorResponse translate(List<String> texts, TargetTranslateContext contextLang) throws Exception;
}
