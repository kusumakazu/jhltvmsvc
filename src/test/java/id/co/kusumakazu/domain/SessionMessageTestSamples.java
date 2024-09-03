package id.co.kusumakazu.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SessionMessageTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static SessionMessage getSessionMessageSample1() {
        return new SessionMessage().id(1L).role("role1").content("content1").aiSessionId(1L);
    }

    public static SessionMessage getSessionMessageSample2() {
        return new SessionMessage().id(2L).role("role2").content("content2").aiSessionId(2L);
    }

    public static SessionMessage getSessionMessageRandomSampleGenerator() {
        return new SessionMessage()
            .id(longCount.incrementAndGet())
            .role(UUID.randomUUID().toString())
            .content(UUID.randomUUID().toString())
            .aiSessionId(longCount.incrementAndGet());
    }
}
