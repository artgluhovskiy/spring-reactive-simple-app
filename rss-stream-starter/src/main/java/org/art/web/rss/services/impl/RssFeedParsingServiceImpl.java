package org.art.web.rss.services.impl;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.art.web.rss.model.RssArticle;
import org.art.web.rss.predicates.ArticleInitializedPredicate;
import org.art.web.rss.services.RssFeedParsingService;
import org.art.web.rss.utils.xml.StaxStreamProcessor;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static org.art.web.rss.services.RssFeedServiceConstants.*;

@Log4j2
public class RssFeedParsingServiceImpl implements RssFeedParsingService<RssArticle> {

    private static final DateTimeFormatter RFC822_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("EEE, d[d] MMM yyyy HH:mm[:ss] z", Locale.US);

    @Override
    public List<RssArticle> parseRssFeedRawData(String feed) {
        log.info("RssFeedParseServiceImpl: parsing RSS feed raw xml file");
        if (StringUtils.isBlank(feed)) {
            return Collections.emptyList();
        }
        List<RssArticle> articles = new ArrayList<>();
        InputStream feedInStream = new ByteArrayInputStream(feed.getBytes(StandardCharsets.UTF_8));
        RssArticle currentArticle = new RssArticle();
        try (StaxStreamProcessor processor = new StaxStreamProcessor(feedInStream, StandardCharsets.UTF_8.name())) {
            XMLStreamReader reader = processor.getReader();
            String tagContent = StringUtils.EMPTY;
            while (reader.hasNext()) {
                int event = reader.next();
                switch (event) {
                    case XMLStreamConstants.START_ELEMENT:
                        if (XML_ARTICLE_TAG_NAME_ITEM.equals(reader.getLocalName())) {
                            currentArticle = new RssArticle();
                        }
                        break;
                    case XMLStreamConstants.CHARACTERS:
                        tagContent = reader.getText().trim();
                        break;
                    case XMLStreamConstants.END_ELEMENT:
                        switch (reader.getLocalName()) {
                            case XML_ARTICLE_TAG_NAME_ITEM:
                                articles.add(currentArticle);
                                break;
                            case XML_ARTICLE_TAG_NAME_TITLE:
                                currentArticle.setTitle(tagContent);
                                break;
                            case XML_ARTICLE_TAG_NAME_DESCRIPTION:
                                currentArticle.setDescription(tagContent);
                                break;
                            case XML_ARTICLE_TAG_NAME_LINK:
                                currentArticle.setLink(tagContent);
                                break;
                            case XML_ARTICLE_TAG_NAME_PUB_DATE:
                                currentArticle.setPubDate(LocalDateTime.parse(tagContent, RFC822_DATE_TIME_FORMATTER));
                                break;
                        }
                        break;
                }
            }
        } catch (Exception e) {
            log.warn("Exception while reading/parsing RSS feed xml", e);
        }
        articles = articles.stream()
                .filter(new ArticleInitializedPredicate())
                .collect(Collectors.toList());
        log.debug("Parsed {} RSS feed articles: {}", articles.size(), articles);
        return articles;
    }
}
