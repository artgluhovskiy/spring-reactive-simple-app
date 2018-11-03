package org.art.web.rss.predicates;

import org.apache.commons.lang3.StringUtils;
import org.art.web.rss.model.RssArticle;

import java.time.LocalDateTime;
import java.util.function.Predicate;

public class ArticleInitializedPredicate implements Predicate<RssArticle> {

    @Override
    public boolean test(RssArticle rssArticle) {
        if (rssArticle == null) {
            return false;
        }
        String title = rssArticle.getTitle();
        String description = rssArticle.getDescription();
        String link = rssArticle.getLink();
        LocalDateTime pubDate = rssArticle.getPubDate();
        String pubDateStr = pubDate != null ? pubDate.toString() : StringUtils.EMPTY;
        return StringUtils.isNotBlank(title)
                && StringUtils.isNotBlank(description)
                && StringUtils.isNotBlank(link)
                && StringUtils.isNotBlank(pubDateStr);
    }
}
