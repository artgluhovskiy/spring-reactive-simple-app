package org.art.web.rss.configuration.initializer;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

@Log4j2
public class RssStreamAppContextInitializer implements ApplicationContextInitializer {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        String rssServiceMode = environment.getProperty("rss-streaming-service.rss-service-mode", String.class);
        log.info("Context initialization...Properties: app mode - {}", rssServiceMode);
    }
}
