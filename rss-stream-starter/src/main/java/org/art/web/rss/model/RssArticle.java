package org.art.web.rss.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RssArticle {

    private String title;
    private String description;
    private String link;
    private LocalDateTime pubDate;
}
