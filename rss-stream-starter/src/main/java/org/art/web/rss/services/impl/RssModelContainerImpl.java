package org.art.web.rss.services.impl;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.art.web.rss.listeners.RssFeedContainerListener;
import org.art.web.rss.model.RssArticle;
import org.art.web.rss.services.RssModelContainer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Log4j2
@Service
public class RssModelContainerImpl implements RssModelContainer<RssArticle> {

    private static final int MAX_ARTICLE_QUEUE_SIZE = 40;

    private static final BlockingQueue<RssArticle> ARTICLE_QUEUE = new LinkedBlockingQueue<>(MAX_ARTICLE_QUEUE_SIZE);

    private static final Set<RssFeedContainerListener<RssArticle>> RSS_LISTENERS = ConcurrentHashMap.newKeySet();

    @Getter @Setter
    private volatile boolean streamActive = true;

    public RssModelContainerImpl() {
        launchContainerHelper();
    }

    @Override
    public int pushArticles(List<RssArticle> articles) {
        log.info("Pushing new articles in to the queue: {}", articles);
        int pushedArticlesNumber = 0;
        if (articles == null || articles.isEmpty()) {
            return pushedArticlesNumber;
        }
        try {
            for (RssArticle article : articles) {
                if (ARTICLE_QUEUE.offer(article, 5, TimeUnit.SECONDS)) {
                    pushedArticlesNumber++;
                }
            }
        } catch (InterruptedException e) {
            log.warn("Exception while pushing new articles in to the queue", e);
        }
        log.debug("Number of pushed articles: {}", pushedArticlesNumber);
        log.info("Number of articles in the queue: {}", ARTICLE_QUEUE.size());
        return pushedArticlesNumber;
    }

    @Override
    public void registerRssConsumer(RssFeedContainerListener<RssArticle> listener) {
        if (listener != null) {
            RSS_LISTENERS.add(listener);
        }
    }

    private void launchContainerHelper() {
        Thread helper = new Thread(() -> {
            log.info("Container helper is running...");
            while (streamActive) {
                try {
                    RssArticle article = ARTICLE_QUEUE.take();
                    RSS_LISTENERS.forEach(listener -> listener.onArticlePushed(article));
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    log.warn("Exception while retrieving new article out of the queue");
                }
            }
        });
        helper.setDaemon(true);
        helper.start();
    }
}
