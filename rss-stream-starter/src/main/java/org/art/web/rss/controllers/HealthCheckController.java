package org.art.web.rss.controllers;

import org.art.web.rss.model.SimpleArticleModel;
import org.art.web.rss.services.RssFeedImportService;
import org.art.web.rss.services.RssFeedParseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/health")
public class HealthCheckController {

    private final RssFeedImportService rssFeedImportService;

    private final RssFeedParseService rssFeedParseService;

    @Autowired
    public HealthCheckController(RssFeedImportService rssFeedImportService, RssFeedParseService rssFeedParseService) {
        this.rssFeedImportService = rssFeedImportService;
        this.rssFeedParseService = rssFeedParseService;
    }

    @GetMapping("/ping")
    public String ping() {
        return "I'm running...";
    }

    @GetMapping("/rss")
    public String testRssImport() {
        List<String> rssSources = new ArrayList<>();
        rssSources.add("https://www.nasa.gov/rss/dyn/breaking_news.rss");
        String feed = rssFeedImportService.importRssFeedRaw(rssSources);
        List<SimpleArticleModel> simpleArticleModels = rssFeedParseService.parseRssFeedRawData(feed);
        return "Rss OK";
    }
}
