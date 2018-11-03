package org.art.web.rss.configuration;

import lombok.extern.log4j.Log4j2;
import org.art.web.rss.annotations.ConditionalOnRssStreamActivation;
import org.springframework.context.annotation.Configuration;

@Log4j2
@Configuration
@ConditionalOnRssStreamActivation
public class RssStreamAppConfig {


}
