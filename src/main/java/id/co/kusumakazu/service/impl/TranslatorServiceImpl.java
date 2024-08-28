package id.co.kusumakazu.service.impl;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.ibm.icu.text.Transliterator;
import id.co.kusumakazu.domain.TargetTranslateContext;
import id.co.kusumakazu.domain.response.translator.DeepLXTranslatorResponse;
import id.co.kusumakazu.service.TranslatorService;
import id.co.kusumakazu.web.rest.RestClient;
import java.util.List;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TranslatorServiceImpl implements TranslatorService {

    private final Logger log = LoggerFactory.getLogger(TranslatorServiceImpl.class);

    private static final Pattern alphaReg = Pattern.compile("^[a-zA-Z]+$");

    @Autowired
    private RestClient restclient;

    @Override
    public String convert(String text) {
        log.info("converting text : {}", text);
        Transliterator latinToKatakana = Transliterator.getInstance("Latin-Katakana");
        String converted = latinToKatakana.transliterate(text);
        log.info("converted text : {}", converted);
        return converted;
    }

    @Override
    public DeepLXTranslatorResponse translate(List<String> texts, TargetTranslateContext contextLang) throws Exception {
        log.info("translate Text : {}, target lang -> {}", texts, contextLang);
        Gson gson = new Gson();
        DeepLXTranslatorResponse resultResponse = gson.fromJson(
            restclient.sendTranslate(texts, contextLang).getBody(),
            new TypeToken<DeepLXTranslatorResponse>() {}.getType()
        );
        log.info("resultResponse : {}", resultResponse);
        return resultResponse;
    }
}
