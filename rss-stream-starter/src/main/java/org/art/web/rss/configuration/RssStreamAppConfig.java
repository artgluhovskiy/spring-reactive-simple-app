package org.art.web.rss.configuration;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.art.web.rss.annotations.ConditionalOnRssStreamActivation;
import org.art.web.rss.listeners.RssStreamServiceListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Log4j2
@Configuration
@ConditionalOnRssStreamActivation
public class RssStreamAppConfig {

    @Bean
    public RssStreamServiceListener rssStreamAppConfig() {
        log.info("Bean creation: RssStreamServiceListener");
        return new RssStreamServiceListener();
    }
}
