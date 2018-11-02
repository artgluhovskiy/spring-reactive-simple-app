package org.art.web.rss.predicates;

import org.apache.commons.lang3.StringUtils;
import org.art.web.rss.model.RssArticleModel;

import java.time.LocalDateTime;
import java.util.function.Predicate;

public class ArticleInitializedPredicate implements Predicate<RssArticleModel> {

    @Override
    public boolean test(RssArticleModel rssArticleModel) {
        if (rssArticleModel == null) {
            return false;
        }
        String title = rssArticleModel.getTitle();
        String description = rssArticleModel.getDescription();
        String link = rssArticleModel.getLink();
        LocalDateTime pubDate = rssArticleModel.getPubDate();
        String pubDateStr = pubDate != null ? pubDate.toString() : StringUtils.EMPTY;
        return StringUtils.isNotBlank(title)
                && StringUtils.isNotBlank(description)
                && StringUtils.isNotBlank(link)
                && StringUtils.isNotBlank(pubDateStr);
    }
}
