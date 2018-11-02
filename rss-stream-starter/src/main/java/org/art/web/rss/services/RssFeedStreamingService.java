package org.art.web.rss.services;

import org.art.web.rss.model.RssArticleModel;
import reactor.core.publisher.Flux;

import java.util.List;

public interface RssFeedStreamingService {

    Flux<RssArticleModel> prepareRssArticlesFlux();
    int pushArticles(List<RssArticleModel> articles);
}
