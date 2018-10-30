package org.art.web.rss;

import lombok.extern.slf4j.Slf4j;
import org.art.web.rss.configuration.properties.RssStreamServiceProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@Slf4j
@SpringBootApplication
@EnableConfigurationProperties
public class RssStreamingApplication implements CommandLineRunner {

    private final RssStreamServiceProperties properties;

    @Autowired
    public RssStreamingApplication(RssStreamServiceProperties properties) {
        this.properties = properties;
    }

    public static void main(String[] args) {
        SpringApplication.run(RssStreamingApplication.class);
    }

    @Override
    public void run(String... args) {
        log.info("RSS Service mode: {}, RSS sources: {}", properties.getRssServiceMode(), properties.getRssSources());
    }
}
