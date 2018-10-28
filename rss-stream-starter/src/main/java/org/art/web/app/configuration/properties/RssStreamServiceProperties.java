package org.art.web.app.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(value = "rss-streaming-service")
public class RssStreamServiceProperties {

    List<String> rssSources;
    String rssServiceMode;
}
