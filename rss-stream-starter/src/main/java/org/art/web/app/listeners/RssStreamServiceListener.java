package org.art.web.app.listeners;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

@Slf4j
public class RssStreamServiceListener implements ApplicationListener<ContextRefreshedEvent> {

    public RssStreamServiceListener() {
        log.info("Listener initialization ...");
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
    }
}
