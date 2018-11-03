package org.art.web.rss.services;

import java.util.List;

public interface RssFeedParsingService<T> {

    List<T> parseRssFeedRawData(String feed);

}
