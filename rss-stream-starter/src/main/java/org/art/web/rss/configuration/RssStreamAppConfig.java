package org.art.web.rss.configuration;

import lombok.extern.log4j.Log4j2;
import org.art.web.rss.annotations.ConditionalOnStreamingMode;
import org.art.web.rss.configuration.properties.RssStreamServiceProperties;
import org.art.web.rss.configuration.runners.RssArticleDataInitializer;
import org.art.web.rss.model.RssArticle;
import org.art.web.rss.scheduling.ScheduledTasks;
import org.art.web.rss.services.RssFeedImportingService;
import org.art.web.rss.services.RssFeedParsingService;
import org.art.web.rss.services.RssModelContainer;
import org.art.web.rss.services.impl.RssFeedImportingServiceImpl;
import org.art.web.rss.services.impl.RssFeedParsingServiceImpl;
import org.art.web.rss.services.impl.RssModelContainerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Application configuration eligible for "streaming" app mode.
 */
@Log4j2
@Configuration
@ConditionalOnStreamingMode
public class RssStreamAppConfig {

    @Bean
    public RssFeedImportingService rssFeedImportingService() {
        log.info("RssFeedImportingServiceImpl bean initialization...");
        return new RssFeedImportingServiceImpl();
    }

    @Bean
    public RssFeedParsingService rssFeedParsingService() {
        log.info("RssFeedParsingServiceImpl bean initialization...");
        return new RssFeedParsingServiceImpl();
    }

    @Bean
    public RssModelContainer rssModelContainer() {
        log.info("RssModelContainerImpl bean initialization...");
        return new RssModelContainerImpl();
    }

    @Bean
    public RssArticleDataInitializer rssArticleDataInitializer(RssStreamServiceProperties properties,
                                                               RssFeedImportingService importingService,
                                                               RssFeedParsingService<RssArticle> parsingService,
                                                               RssModelContainer<RssArticle> container) {
        log.info("RssArticleDataInitializer bean initialization...");
        return new RssArticleDataInitializer(properties, importingService, parsingService, container);
    }

    @Bean
    public ScheduledTasks scheduledTasks(RssStreamServiceProperties properties,
                                         RssFeedImportingService importingService,
                                         RssFeedParsingService<RssArticle> parsingService,
                                         RssModelContainer<RssArticle> container) {
        log.info("ScheduledTasks bean initialization...");
        return new ScheduledTasks(importingService, parsingService, container, properties);
    }
}
