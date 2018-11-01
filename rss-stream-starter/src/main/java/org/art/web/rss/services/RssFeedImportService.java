package org.art.web.rss.services;

import java.util.List;

public interface RssFeedImportService {

    String importRssFeedRaw(List<String> feedSources);
}
