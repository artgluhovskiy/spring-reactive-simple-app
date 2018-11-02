package org.art.web.rss.configuration.runners;

import lombok.extern.log4j.Log4j2;
import org.art.web.rss.configuration.properties.RssStreamServiceProperties;
import org.art.web.rss.model.RssArticleModel;
import org.art.web.rss.services.RssFeedImportingService;
import org.art.web.rss.services.RssFeedParsingService;
import org.art.web.rss.services.RssFeedStreamingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.art.web.rss.configuration.ConfigConstants.INITIAL_RSS_FEED_SOURCE;

@Log4j2
@Component
public class RssArticleDataInitializer implements CommandLineRunner {

    @Autowired
    private RssStreamServiceProperties properties;

    @Autowired
    private RssFeedImportingService rssFeedImportingService;

    @Autowired
    private RssFeedParsingService rssFeedParsingService;

    @Autowired
    private RssFeedStreamingService rssFeedStreamingService;

    @Override
    public void run(String... args) {
        log.info("RssArticleDataInitializer is running. Fetching initial articles from remote");
        String runningMode = properties.getRssServiceMode();
        List<String> rssSources = properties.getRssSources();
        log.info("RSS Service mode: {}, RSS sources: {}", runningMode, rssSources);
        if (rssSources != null) {
            try {
                String feedRaw = rssFeedImportingService.importRssFeedRaw(INITIAL_RSS_FEED_SOURCE);
                List<RssArticleModel> rssArticles = rssFeedParsingService.parseRssFeedRawData(feedRaw);
                rssFeedStreamingService.pushArticles(rssArticles);
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
