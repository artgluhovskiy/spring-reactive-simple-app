package org.art.web.rss.services.impl;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.art.web.rss.services.RssFeedImportService;
import org.art.web.rss.utils.http.HttpClientUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.art.web.rss.utils.http.HttpCommonConstants.CONTENT_TYPE_APPLICATION_RSS_XML;

@Log4j2
@Service
public class RssFeedImportServiceImpl implements RssFeedImportService {

    @Override
    public String importRssFeedRaw(List<String> feedSources) {
        log.info("Getting RSS feed xml from the sources: {}", feedSources);
        if (feedSources == null) {
            throw new IllegalArgumentException("The collection with feed sources should not be null");
        }
        String feedSource = StringUtils.EMPTY;
        String feedRaw = StringUtils.EMPTY;
        try (CloseableHttpClient client = HttpClientUtils.buildRssFeedImporterHttpClient()) {
            for (String feed : feedSources) {
                feedSource = feed;
                HttpGet getRequest = new HttpGet(feedSource);
                HttpResponse response = client.execute(getRequest);
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    String contentMimeType = ContentType.getOrDefault(response.getEntity()).getMimeType();
                    if (contentMimeType.contains(CONTENT_TYPE_APPLICATION_RSS_XML)) {
                        feedRaw = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8.displayName());
                        log.debug("RSS feed xml: {}", feedRaw);
                    }
                }
            }
        } catch (IOException e) {
            log.info("Cannot retrieve RSS feed xml from {}", feedSource, e);
        }
        return feedRaw;
    }
}
