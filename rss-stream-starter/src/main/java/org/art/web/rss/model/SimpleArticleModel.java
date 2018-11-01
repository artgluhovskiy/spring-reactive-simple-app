package org.art.web.rss.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SimpleArticleModel {

    private String title;
    private String description;
    private String link;
}
