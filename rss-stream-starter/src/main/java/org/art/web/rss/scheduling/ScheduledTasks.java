package org.art.web.rss.scheduling;

import lombok.extern.log4j.Log4j2;
import org.art.web.rss.configuration.properties.RssStreamServiceProperties;
import org.art.web.rss.model.RssArticleModel;
import org.art.web.rss.services.RssFeedImportingService;
import org.art.web.rss.services.RssFeedParsingService;
import org.art.web.rss.services.RssFeedStreamingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Component
public class ScheduledTasks {

    @Autowired
    private RssFeedImportingService rssFeedImportingService;

    @Autowired
    private RssFeedParsingService rssFeedParsingService;

    @Autowired
    private RssFeedStreamingService rssFeedStreamingService;

    @Autowired
    private RssStreamServiceProperties properties;

    private static volatile LocalDateTime globalTimestamp = LocalDateTime.now().minusDays(100);

    @Scheduled(fixedRate = 20000)
    public void fetchRssFeedFromSources() {
        log.info("Scheduler: fetching RSS feed articles... Thread: {}", Thread.currentThread().getName());
        List<String> rssSources = properties.getRssSources();
        log.info("Scheduler: fetching RSS feed from sources: {}", rssSources);
        LocalDateTime glTms = globalTimestamp;
        if (rssSources != null) {
            try {
                rssSources.forEach(source -> {
                    log.debug("Scheduler: local time of RSS fetching: {}", glTms);
                    String feedRaw = rssFeedImportingService.importRssFeedRaw(source);
                    List<RssArticleModel> rssArticles = rssFeedParsingService.parseRssFeedRawData(feedRaw);
                    rssArticles = rssArticles.stream()
                            .filter(article -> glTms.isBefore(article.getPubDate()))
                            .collect(Collectors.toList());
                    rssFeedStreamingService.pushArticles(rssArticles);
                    if (!rssArticles.isEmpty()) {
                        log.debug("New Rss Articles were fetched from the source {}: {}", source, rssArticles);
                    }
                });
                globalTimestamp = LocalDateTime.now();
            } catch (Exception e) {
                log.warn("Exception while fetching/generating articles from the sources", e);
            }
        } else {
            log.info("RSS feed sources are not defined");
        }
    }
}
