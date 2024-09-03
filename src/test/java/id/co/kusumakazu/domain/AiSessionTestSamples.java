package id.co.kusumakazu.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AiSessionTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static AiSession getAiSessionSample1() {
        return new AiSession().id(1L).sessionName("sessionName1");
    }

    public static AiSession getAiSessionSample2() {
        return new AiSession().id(2L).sessionName("sessionName2");
    }

    public static AiSession getAiSessionRandomSampleGenerator() {
        return new AiSession().id(longCount.incrementAndGet()).sessionName(UUID.randomUUID().toString());
    }
}
