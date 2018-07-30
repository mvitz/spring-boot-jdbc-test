package de.mvitz.spring.boot.jdbc.test.support;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import ru.yandex.qatools.embed.postgresql.EmbeddedPostgres;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.Map;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.springframework.util.SocketUtils.findAvailableTcpPort;
import static ru.yandex.qatools.embed.postgresql.distribution.Version.Main.V9_6;

@Target(TYPE)
@Retention(RUNTIME)
@Documented
@Inherited
@ImportAutoConfiguration(classes = EmbeddedPostgresSupport.EmbeddedPostgresConfiguration.class)
public @interface EmbeddedPostgresSupport {

    @TestConfiguration
    class EmbeddedPostgresConfiguration {

        @Bean
        BeanFactoryPostProcessor dataSourcePropertiesOverrideConfigurer() {
            return (beanFactory) -> {
                Map<String, Object> dataSourcePropertiesOverride = new HashMap<>();
                dataSourcePropertiesOverride.put("database.port", findAvailableTcpPort(10000));
                dataSourcePropertiesOverride.put("spring.datasource.url", "jdbc:postgresql://localhost:${database.port}/postgres?stringtype=unspecified");
                dataSourcePropertiesOverride.put("spring.datasource.username", "username");
                dataSourcePropertiesOverride.put("spring.datasource.password", "password");

                ConfigurableEnvironment configurableEnvironment = beanFactory.getBean(ConfigurableEnvironment.class);
                configurableEnvironment.getPropertySources().addFirst(new MapPropertySource("dataSourcePropertiesOverride", dataSourcePropertiesOverride));
            };
        }

        @Bean
        EmbeddedPostgresManager embeddedPostgresManager(@Value("${database.port}") int databasePort) {
            return new EmbeddedPostgresManager(databasePort);
        }
    }

    class EmbeddedPostgresManager {

        private final int databasePort;
        private EmbeddedPostgres embeddedPostgres;

        EmbeddedPostgresManager(int databasePort) {
            this.databasePort = databasePort;
        }

        @PostConstruct
        public void startEmbeddedPostgres() throws IOException {
            this.embeddedPostgres = new EmbeddedPostgres(V9_6);
            this.embeddedPostgres.start("localhost", this.databasePort, "database", "username", "password");
        }

        @PreDestroy
        public void stopEmbeddedPostgres() {
            this.embeddedPostgres.stop();
            this.embeddedPostgres = null;
        }
    }
}
