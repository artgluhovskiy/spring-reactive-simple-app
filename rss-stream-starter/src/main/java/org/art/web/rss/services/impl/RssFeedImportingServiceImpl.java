package org.art.web.rss.services.impl;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.art.web.rss.services.RssFeedImportingService;
import org.art.web.rss.utils.http.HttpClientUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.art.web.rss.utils.http.HttpCommonConstants.CONTENT_TYPE_APPLICATION_RSS_XML;
import static org.art.web.rss.utils.http.HttpCommonConstants.CONTENT_TYPE_APPLICATION_XML;

@Log4j2
@Service
public class RssFeedImportingServiceImpl implements RssFeedImportingService {

    private static final Set<String> XML_MIME_TYPES;

    static {
        Set<String> mimeXmlTypes = new HashSet<>();
        mimeXmlTypes.add(CONTENT_TYPE_APPLICATION_RSS_XML);
        mimeXmlTypes.add(CONTENT_TYPE_APPLICATION_XML);
        XML_MIME_TYPES = Collections.unmodifiableSet(mimeXmlTypes);
    }

    @Override
    public String importRssFeedRaw(String feedSource) {
        log.info("Getting RSS feed xml from the sources: {}", feedSource);
        if (feedSource == null) {
            throw new IllegalArgumentException("The collection with feed sources should not be null");
        }
        String feedRaw = StringUtils.EMPTY;
        try (CloseableHttpClient client = HttpClientUtils.buildRssFeedImporterHttpClient()) {
            HttpGet getRequest = new HttpGet(feedSource);
            HttpResponse response = client.execute(getRequest);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String contentMimeType = ContentType.getOrDefault(response.getEntity()).getMimeType();
                if (XML_MIME_TYPES.contains(contentMimeType)) {
                    feedRaw = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8.displayName());
                    log.debug("RSS feed xml: {}", feedRaw);
                }
            }

        } catch (IOException e) {
            log.warn("Cannot retrieve RSS feed xml from {}", feedSource, e);
        }
        return feedRaw;
    }
}
