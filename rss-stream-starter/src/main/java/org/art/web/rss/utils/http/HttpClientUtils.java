package org.art.web.rss.utils.http;

import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.springframework.http.MediaType;

import java.util.Collections;

/**
 * Http Client Utility class for building
 * a simple http client, which is used to retrieve
 * RSS data files from remote servers.
 */
public class HttpClientUtils {

    public static CloseableHttpClient buildRssFeedImporterHttpClient() {
        HttpClientBuilder clientBuilder = HttpClientBuilder.create();
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(1000)
                .setConnectionRequestTimeout(1000)
                .setSocketTimeout(1000).build();
        clientBuilder
                .setDefaultRequestConfig(config)
                .setDefaultHeaders(Collections.singletonList(new BasicHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_RSS_XML_VALUE)));
        return clientBuilder.build();
    }
}
