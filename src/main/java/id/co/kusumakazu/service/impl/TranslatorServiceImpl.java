package id.co.kusumakazu.service.impl;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import id.co.kusumakazu.domain.TargetTranslateContext;
import id.co.kusumakazu.domain.response.translator.DeepLXTranslatorResponse;
import id.co.kusumakazu.service.TranslatorService;
import id.co.kusumakazu.web.rest.RestClient;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TranslatorServiceImpl implements TranslatorService {

    private final Logger log = LoggerFactory.getLogger(TranslatorServiceImpl.class);

    @Autowired
    private RestClient restclient;

    @Override
    public DeepLXTranslatorResponse translate(List<String> texts, TargetTranslateContext targetLang) throws Exception {
        log.info("translate Text : {}, target lang -> {}", texts, targetLang);
        Gson gson = new Gson();
        DeepLXTranslatorResponse resultResponse = gson.fromJson(
            restclient.sendTranslate(texts, targetLang).getBody(),
            new TypeToken<DeepLXTranslatorResponse>() {}.getType()
        );
        log.info("Translate completed !");
        return resultResponse;
    }
}
