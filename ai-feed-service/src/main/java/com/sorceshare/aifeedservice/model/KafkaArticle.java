package com.sorceshare.aifeedservice.model;

import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KafkaArticle {

    private String Id;

    private String articleTitle;

    private List<String> articleLabels;

    private String articleSummary;

    private boolean articleSentiment;

    private List<String> articleSources;

    private String articleDate;
}
