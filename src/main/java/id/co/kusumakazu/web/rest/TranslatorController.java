package id.co.kusumakazu.web.rest;

import id.co.kusumakazu.domain.TargetTranslateContext;
import id.co.kusumakazu.domain.response.translator.DeepLXTranslatorResponse;
import id.co.kusumakazu.service.TranslatorService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TranslatorController {

    private final Logger log = LoggerFactory.getLogger(TranslatorController.class);

    @Autowired
    private TranslatorService translatorService;

    @PostMapping("/deeplx/translate")
    public ResponseEntity<DeepLXTranslatorResponse> translate(
        @RequestParam List<String> text,
        @RequestParam TargetTranslateContext contextLang
    ) throws Exception {
        log.debug("REST request to get test");
        DeepLXTranslatorResponse response = translatorService.translate(text, contextLang);
        return ResponseEntity.ok(response);
    }
}
