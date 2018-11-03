package org.art.web.rss.controllers;

import org.art.web.rss.scheduling.ScheduledTasks;
import org.art.web.rss.services.RssFeedImportingService;
import org.art.web.rss.services.RssFeedParsingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthCheckController {

    private final RssFeedImportingService rssFeedImportingService;

    private final RssFeedParsingService rssFeedParsingService;

    private final ScheduledTasks scheduledTasks;

    @Autowired
    public HealthCheckController(RssFeedImportingService rssFeedImportingService, RssFeedParsingService rssFeedParsingService, ScheduledTasks scheduledTasks) {
        this.rssFeedImportingService = rssFeedImportingService;
        this.rssFeedParsingService = rssFeedParsingService;
        this.scheduledTasks = scheduledTasks;
    }

    @GetMapping("/ping")
    public String ping() {
        return "I'm running...";
    }

    @GetMapping("/rss")
    public String testRssImport() {
//        String rssSource = "https://www.nasa.gov/rss/dyn/breaking_news.rss";
        scheduledTasks.fetchRssFeedFromSources();
//        String feed = rssFeedImportingService.importRssFeedRaw(rssSource);
//        List<RssArticle> rssArticleModels = rssFeedParsingService.parseRssFeedRawData(feed);


        return "Rss OK";
    }
}
