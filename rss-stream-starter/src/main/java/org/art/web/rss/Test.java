package org.art.web.rss;

import lombok.extern.log4j.Log4j2;
import org.art.web.rss.client.RssStreamClient;
import org.art.web.rss.listeners.RssFeedContainerListener;
import org.art.web.rss.model.RssArticle;
import reactor.core.Disposable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Log4j2
public class Test {
    public static void main(String[] args) throws InterruptedException {
        List<RssArticle> articles = new ArrayList<>();
        RssStreamClient client = RssStreamClient.getRssStreamClient();
        Disposable disposable1 = client.getRssFlux()
                .subscribe(
                        articleCons -> {
                            log.info("*** 1 Consuming article...");
                            articles.add(articleCons);
                        },
                        articlesErr -> {
                            log.info("*** 1 Error occurred");
                        },
                        () -> {
                            log.info("*** 1 Completed...");
                        },
                        subscription -> {
                            subscription.request(5);
                        });

        Disposable disposable2 = client.getRssFlux()
                .subscribe(
                        articleCons -> {
                            log.info("*** 2 Consuming article...");
                            articles.add(articleCons);
                        },
                        articlesErr -> {
                            log.info("*** 2 Error occurred");
                        },
                        () -> {
                            log.info("*** 2 Completed...");
                        },
                        subscription -> {
                            subscription.request(5);
                        });


        TimeUnit.SECONDS.sleep(30);

        disposable1.dispose();
        log.info("*** 1 Disposed...");

        TimeUnit.SECONDS.sleep(20);

        disposable2.dispose();
        log.info("*** 2 Disposed...");

        TimeUnit.MINUTES.sleep(4);


        System.out.println("*** Articles size: " + articles.size());

//        List<RssFeedContainerListener<RssArticle>> list = new ArrayList<>();
//        list.add(new RssFeedContainerListener<RssArticle>() {
//
//            private String id = UUID.randomUUID().toString();
//
//            @Override
//            public void onArticlePushed(RssArticle article) {
//
//            }
//
//            @Override
//            public void onRssStreamClosed() {
//
//            }
//
//            @Override
//            public String getGlobalClientId() {
//                return id;
//            }
//        });
//        list.forEach(art -> System.out.println("Article: " + art + ", article id: " + art.getGlobalClientId()));
    }
}
