package org.art.web.rss.services;

import org.art.web.rss.model.RssArticleModel;

import java.util.List;

public interface RssFeedParsingService {

    List<RssArticleModel> parseRssFeedRawData(String feed);

}
