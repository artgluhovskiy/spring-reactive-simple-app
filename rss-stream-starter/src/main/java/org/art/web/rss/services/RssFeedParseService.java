package org.art.web.rss.services;

import org.art.web.rss.model.SimpleArticleModel;

import java.util.List;

public interface RssFeedParseService {

    List<SimpleArticleModel> parseRssFeedRawData(String feed);

}
