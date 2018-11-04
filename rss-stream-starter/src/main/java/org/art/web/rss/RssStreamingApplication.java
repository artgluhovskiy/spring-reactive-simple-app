package org.art.web.rss;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@Log4j2
@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties
public class RssStreamingApplication {

    public static void main(String[] args) {
        SpringApplication.run(RssStreamingApplication.class);
    }
}
