package org.art.web.rss.configuration.runners;

import lombok.extern.log4j.Log4j2;
import org.art.web.rss.configuration.properties.RssStreamServiceProperties;
import org.art.web.rss.model.RssArticle;
import org.art.web.rss.services.RssFeedImportingService;
import org.art.web.rss.services.RssFeedParsingService;
import org.art.web.rss.services.RssModelContainer;
import org.springframework.boot.CommandLineRunner;

import java.util.List;

import static org.art.web.rss.configuration.ConfigConstants.INITIAL_RSS_FEED_SOURCE;

@Log4j2
public class RssArticleDataInitializer implements CommandLineRunner {

    private final RssStreamServiceProperties properties;

    private final RssFeedImportingService rssFeedImportingService;

    private final RssFeedParsingService<RssArticle> rssFeedParsingService;

    private final RssModelContainer<RssArticle> rssModelContainer;

    public RssArticleDataInitializer(RssStreamServiceProperties properties,
                                     RssFeedImportingService rssFeedImportingService,
                                     RssFeedParsingService<RssArticle> rssFeedParsingService,
                                     RssModelContainer<RssArticle> rssModelContainer) {
        this.properties = properties;
        this.rssFeedImportingService = rssFeedImportingService;
        this.rssFeedParsingService = rssFeedParsingService;
        this.rssModelContainer = rssModelContainer;
    }

    @Override
    public void run(String... args) {
        log.info("RssArticleDataInitializer is running. Fetching initial articles from remote");
        List<String> rssSources = properties.getRssSources();
        if (rssSources != null) {
            try {
                String feedRaw = rssFeedImportingService.importRssFeedRaw(INITIAL_RSS_FEED_SOURCE);
                List<RssArticle> rssArticles = rssFeedParsingService.parseRssFeedRawData(feedRaw);
                rssModelContainer.pushArticles(rssArticles);
                if (!rssArticles.isEmpty()) {
                    log.debug("Initial Rss Articles were fetched from the source {}: {}", INITIAL_RSS_FEED_SOURCE, rssArticles);
                }
            } catch (Exception e) {
                log.error("Exception while fetching/generating articles from the sources", e);
            }
        } else {
            log.info("RSS feed sources are not defined");
        }
    }
}
