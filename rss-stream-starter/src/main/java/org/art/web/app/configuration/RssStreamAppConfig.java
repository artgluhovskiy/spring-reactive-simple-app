package org.art.web.app.configuration;

import lombok.extern.slf4j.Slf4j;
import org.art.web.app.annotations.ConditionalOnRssStreamActivation;
import org.art.web.app.listeners.RssStreamServiceListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ConditionalOnRssStreamActivation
public class RssStreamAppConfig {

    @Bean
    public RssStreamServiceListener rssStreamAppConfig() {
        log.info("Bean creation: RssStreamServiceListener");
        return new RssStreamServiceListener();
    }
}
