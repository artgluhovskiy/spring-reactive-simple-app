package org.art.web.rss.utils.http;

import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;

import java.util.Collections;

import static org.art.web.rss.utils.http.HttpCommonConstants.CONTENT_TYPE_APPLICATION_RSS_XML;

public class HttpClientUtils {

    public static CloseableHttpClient buildRssFeedImporterHttpClient() {
        HttpClientBuilder clientBuilder = HttpClientBuilder.create();
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(1000)
                .setConnectionRequestTimeout(1000)
                .setSocketTimeout(1000).build();
        clientBuilder
                .setDefaultRequestConfig(config)
                .setDefaultHeaders(Collections.singletonList(new BasicHeader(HttpHeaders.ACCEPT, CONTENT_TYPE_APPLICATION_RSS_XML)));
        return clientBuilder.build();
    }
}
