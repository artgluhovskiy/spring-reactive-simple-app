package org.art.web.rss;

import lombok.extern.log4j.Log4j2;
import org.art.web.rss.client.RssStreamClient;
import org.art.web.rss.model.RssArticle;
import reactor.core.Disposable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Log4j2
public class Test {
    public static void main(String[] args) throws InterruptedException {
        List<RssArticle> articles = new ArrayList<>();
        RssStreamClient client = RssStreamClient.getRssStreamClient();
        Disposable disposable = client.getRssFlux()
                .subscribe(
                        articleCons -> {
                            log.info("*** Consuming article...");
                            articles.add(articleCons);
                        },
                        articlesErr -> {
                            log.info("*** Error occurred");
                        },
                        () -> {
                            log.info("*** Completed...");
                        });


        TimeUnit.SECONDS.sleep(20);

        disposable.dispose();
        log.info("*** Disposed...");

        TimeUnit.MINUTES.sleep(2);


        System.out.println("*** Articles size: " + articles.size());
    }
}
