package org.art.web.rss.services.impl;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.art.web.rss.model.SimpleArticleModel;
import org.art.web.rss.services.RssFeedService;
import org.art.web.rss.utils.http.HttpClientUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.art.web.rss.utils.http.HttpCommonConstants.CONTENT_TYPE_APPLICATION_RSS_XML;

@Log4j2
@Service
public class RssFeedServiceImpl implements RssFeedService {

    @Override
    public List<SimpleArticleModel> getRssArticles(List<String> feedSources) {
        log.info("Getting RSS feed from the sources: {}", feedSources);
        List<SimpleArticleModel> rssModels = new ArrayList<>();
        String feedSource = StringUtils.EMPTY;
        try (CloseableHttpClient client = HttpClientUtils.buildRssFeedImporterHttpClient()) {
            for (String feed : feedSources) {
                feedSource = feed;
                HttpGet getRequest = new HttpGet(feedSource);
                HttpResponse response = client.execute(getRequest);
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    String contentMimeType = ContentType.getOrDefault(response.getEntity()).getMimeType();
                    if (contentMimeType.contains(CONTENT_TYPE_APPLICATION_RSS_XML)) {
                        String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8.displayName());
//                        rssModels.add();
                    }
                }
            }
        } catch (IOException e) {
            log.info("Cannot retrieve RSS feed from {}", feedSource, e);
        }
        return rssModels;
    }

}
