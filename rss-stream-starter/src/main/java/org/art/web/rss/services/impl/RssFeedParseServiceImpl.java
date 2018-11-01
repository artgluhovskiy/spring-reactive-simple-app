package org.art.web.rss.services.impl;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.art.web.rss.model.SimpleArticleModel;
import org.art.web.rss.services.RssFeedParseService;
import org.springframework.stereotype.Service;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.art.web.rss.services.RssFeedServiceConstants.*;

@Log4j2
@Service
public class RssFeedParseServiceImpl implements RssFeedParseService {

    @Override
    public List<SimpleArticleModel> parseRssFeedRawData(String feed) {
        log.info("RssFeedParseServiceImpl: parsing RSS feed raw xml file: {}", feed);
        List<SimpleArticleModel> articles = new ArrayList<>();
        XMLInputFactory factory = XMLInputFactory.newFactory();
        InputStream feedStream = new ByteArrayInputStream(feed.getBytes(StandardCharsets.UTF_8));
        try {
            XMLStreamReader reader = factory.createXMLStreamReader(feedStream, StandardCharsets.UTF_8.name());
            SimpleArticleModel currentArticle = new SimpleArticleModel();
            String tagContent = StringUtils.EMPTY;
            while (reader.hasNext()) {
                int event = reader.next();
                switch (event) {
                    case XMLStreamConstants.START_ELEMENT:
                        if (XML_ARTICLE_TAG_NAME_ITEM.equals(reader.getLocalName())) {
                            currentArticle = new SimpleArticleModel();
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
                        }
                        break;
                }
            }
        } catch (XMLStreamException e) {
            log.info("Exception while reading RSS feed xml", e);
        }
        log.info("Parsed RSS feed articles: {}", articles);
        return articles;
    }
}
