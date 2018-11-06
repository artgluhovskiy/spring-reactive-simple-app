package org.art.web.rss.listeners;

public interface RssFeedContainerListener<T> {

    void onArticlePushed(T article);

    void onRssStreamClosed();

    String getGlobalClientId();
}
