package com.sorceshare.aifeedservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "articles")
public class Article {

    @Id
    private String id;

    @Indexed(unique = true)
    private String articleTitle;

    private List<String> articleLabels;

    private String articleSummary;

    private boolean articleSentiment;

    private List<String> articleSources;

    private String articleDate;

}
