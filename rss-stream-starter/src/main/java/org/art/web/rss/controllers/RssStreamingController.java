package org.art.web.rss.controllers;

import lombok.extern.log4j.Log4j2;
import org.art.web.rss.annotations.ConditionalOnStreamingMode;
import org.art.web.rss.listeners.RssFeedContainerListener;
import org.art.web.rss.model.RssArticle;
import org.art.web.rss.services.RssModelContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Log4j2
@RestController
@ConditionalOnStreamingMode
@RequestMapping("/rss")
public class RssStreamingController {

    private final RssModelContainer<RssArticle> feedContainer;

    @Autowired
    public RssStreamingController(RssModelContainer feedContainer) {
        this.feedContainer = feedContainer;
    }

    @GetMapping(value = "/stream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<RssArticle> connect() {
        log.info("Connecting to the RSS article stream. RSS consumer registration");
        return Flux.<RssArticle>create(sink -> feedContainer.registerRssConsumer(
                new RssFeedContainerListener<RssArticle>() {
                    @Override
                    public void onArticlePushed(RssArticle article) {
                        log.info("Flux Sink: trying to push new article: {}", article);
                        if (article != null) {
                            sink.next(article);
                        }
                    }

                    @Override
                    public void onRssStreamClosed() {
                        log.info("Flux Sink: completion");
                        sink.complete();
                    }
                })).delayElements(Duration.ofSeconds(5));
    }
}
