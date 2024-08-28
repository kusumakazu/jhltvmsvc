package id.co.kusumakazu.service.impl;

import com.ibm.icu.text.Transliterator;
import id.co.kusumakazu.service.KatakanaConverterService;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class KatakanaConverterServiceImpl implements KatakanaConverterService {

    private final Logger log = LoggerFactory.getLogger(KatakanaConverterServiceImpl.class);

    private static final Pattern alphaReg = Pattern.compile("^[a-zA-Z]+$");

    @Override
    public String convert(String text) {
        log.info("converting text : {}", text);
        Transliterator latinToKatakana = Transliterator.getInstance("Latin-Katakana");
        String converted = latinToKatakana.transliterate(text);
        log.info("converted text : {}", converted);
        return converted;
    }
}
