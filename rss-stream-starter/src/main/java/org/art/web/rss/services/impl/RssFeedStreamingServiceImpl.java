package org.art.web.rss.services.impl;

import lombok.extern.log4j.Log4j2;
import org.art.web.rss.model.RssArticleModel;
import org.art.web.rss.services.RssFeedStreamingService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Log4j2
@Service
public class RssFeedStreamingServiceImpl implements RssFeedStreamingService {

    private static final int MAX_ARTICLE_QUEUE_SIZE = 40;

    private static final BlockingQueue<RssArticleModel> blockingQueue = new LinkedBlockingQueue<>(MAX_ARTICLE_QUEUE_SIZE);

    @Override
    public Flux<RssArticleModel> prepareRssArticlesFlux() {

        return null;
    }

    @Override
    public int pushArticles(List<RssArticleModel> articles) {
        log.info("Pushing new articles in to the queue: {}", articles);
        int pushedArticlesNumber = 0;
        try {
            for (RssArticleModel article : articles) {
                if (blockingQueue.offer(article, 5, TimeUnit.SECONDS)) {
                    pushedArticlesNumber++;
                }
            }
        } catch (InterruptedException e) {
            log.warn("Exception while pushing new articles in to the queue", e);
        }
        log.debug("Number of pushed articles: {}", pushedArticlesNumber);
        log.info("Number of articles in the queue: {}", blockingQueue.size());
        return pushedArticlesNumber;
    }
}
