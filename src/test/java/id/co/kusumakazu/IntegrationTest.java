package id.co.kusumakazu;

import id.co.kusumakazu.config.AsyncSyncConfiguration;
import id.co.kusumakazu.config.EmbeddedRedis;
import id.co.kusumakazu.config.EmbeddedSQL;
import id.co.kusumakazu.config.JacksonConfiguration;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = { JhltvmsvcApp.class, JacksonConfiguration.class, AsyncSyncConfiguration.class })
@EmbeddedRedis
@EmbeddedSQL
public @interface IntegrationTest {
}
