package org.art.web.rss.services;

import org.art.web.rss.listeners.RssFeedContainerListener;

import java.util.List;

public interface RssModelContainer<T> {

    void registerRssConsumer(RssFeedContainerListener<T> listener);

    int pushArticles(List<T> articles);
}
