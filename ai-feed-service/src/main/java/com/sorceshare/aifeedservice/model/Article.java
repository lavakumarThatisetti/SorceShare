package com.sorceshare.aifeedservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Article {

    @Id
    private String id;

    private String articleTitle;

    private String articleLabels;

    private String articleSummary;

    private String articleSentiment;

    private String articleSources;

    private String articleDate;

}
