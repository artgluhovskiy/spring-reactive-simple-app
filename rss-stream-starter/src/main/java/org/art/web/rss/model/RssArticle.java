package org.art.web.rss.model;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class RssArticle {

    private String title;
    private String description;
    private String link;
    private LocalDateTime pubDate;
}
