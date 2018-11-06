package org.art.web.rss.controllers;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.art.web.rss.annotations.ConditionalOnStreamingMode;
import org.art.web.rss.listeners.RssFeedContainerListener;
import org.art.web.rss.model.RssArticle;
import org.art.web.rss.services.RssModelContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Log4j2
@RestController
@ConditionalOnStreamingMode
@RequestMapping("/rss")
public class RssStreamingController {

    private final RssModelContainer<RssArticle> feedContainer;

    @Autowired
    public RssStreamingController(RssModelContainer<RssArticle> feedContainer) {
        this.feedContainer = feedContainer;
    }

    @PostMapping(value = "/stream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<RssArticle> connect(@RequestBody String globalClientId) {
        log.info("Connecting to the RSS article stream. RSS consumer registration. Global ID: {}", globalClientId);
        if (StringUtils.isBlank(globalClientId)) {
            return Flux.empty();
        }
        return Flux.create(sink -> feedContainer.registerRssConsumer(
                new RssFeedContainerListener<RssArticle>() {

                    private String globalId = globalClientId;

                    @Override
                    public void onArticlePushed(RssArticle article) {
                        log.info("Flux Sink: trying to push new article: {}, global ID: {}", article, globalId);
                        if (article != null) {
                            sink.next(article);
                        }
                    }

                    @Override
                    public void onRssStreamClosed() {
                        log.info("Flux Sink: completion, global ID: {}", globalId);
                        sink.complete();
                    }

                    @Override
                    public String toString() {
                        return "Listener {" +
                                "globalId = '" + globalId + '\'' +
                                '}';
                    }

                    @Override
                    public String getGlobalClientId() {
                        return globalId;
                    }
                }));
    }
}
